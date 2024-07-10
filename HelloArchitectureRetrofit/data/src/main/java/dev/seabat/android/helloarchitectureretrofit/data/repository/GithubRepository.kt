package dev.seabat.android.helloarchitectureretrofit.data.repository

import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GithubExceptionConverter
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GithubApiService
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model.GetAllRepoResponse
import dev.seabat.android.helloarchitectureretrofit.domain.entity.AllRepositoryEntity
import dev.seabat.android.helloarchitectureretrofit.domain.entity.OwnerEntity
import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryEntity
import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryListEntity
import dev.seabat.android.helloarchitectureretrofit.domain.exception.ErrorType
import dev.seabat.android.helloarchitectureretrofit.domain.exception.HelloException
import dev.seabat.android.helloarchitectureretrofit.domain.repository.GithubRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val PAGE_SIZE = 30

class GithubRepository(private val endpoint: GithubApiService) : GithubRepositoryContract {

    override suspend fun fetchRepos(query: String?, page: Int): AllRepositoryEntity {
        //NOTE: 同期方式の場合はメインスレッド以外で通信する必要あり
        return withContext(Dispatchers.IO) {
            val response = try {
                // 同期方式で HTTP 通信を行う
                endpoint.getAllRepo(query ?: "architecture", page, PAGE_SIZE).execute()
            } catch (e: Exception) { // 通信自体が失敗した場合
                val exception = HelloException.convertTo(e)
                throw exception
            }

            when {
                response.isSuccessful -> {
                    val responseBody = response.body() ?: throw HelloException.OtherNetworkException(
                        ErrorType.NETWORK_NULL_RESPONSE_BODY,
                        "Response body is null"
                    )
                    val entityList = convertToEntity(page, responseBody)
                    entityList
                }
                else -> {
                    val errorBody = response.errorBody()?.string() ?: ""
                    throw GithubExceptionConverter.convertTo(
                        response.code(),
                        errorBody
                    )
                }
            }
        }
    }

    private fun convertToEntity(page: Int, response: GetAllRepoResponse): AllRepositoryEntity {
        val repos = RepositoryListEntity(
            response.items.map {
                RepositoryEntity(
                    name = it.name,
                    fullName = it.fullName,
                    htmlUrl = it.htmlUrl,
                    description = it.description,
                    createdAt = it.createdAt,
                    owner = OwnerEntity(avatarUrl = it.owner.avatarUrl)
                )
            } as ArrayList<RepositoryEntity>
        )

        val totalPage = when {
            response.total_count == 0 -> 0
            response.total_count < PAGE_SIZE -> 1
            else -> response.total_count / PAGE_SIZE
        }

        return AllRepositoryEntity(
            page =if (totalPage == 0) 0 else page,
            totalPage = totalPage,
            totalCount = response.total_count,
            repos = repos)
    }
}