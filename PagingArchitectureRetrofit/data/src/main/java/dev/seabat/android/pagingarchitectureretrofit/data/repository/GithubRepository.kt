package dev.seabat.android.pagingarchitectureretrofit.data.repository

import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubExceptionConverter
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubApiService
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.model.Repository
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.OwnerEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryListEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.exception.AppException
import dev.seabat.android.pagingarchitectureretrofit.domain.repository.GithubRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class GithubRepository(private val endpoint: GithubApiService) : GithubRepositoryContract {

    override suspend fun fetchRepos(query: String?): RepositoryListEntity? {
        //NOTE: 同期方式の場合はメインスレッド以外で通信する必要あり
        return withContext(Dispatchers.IO) {
            val response = try {
                // 同期方式で HTTP 通信を行う
                endpoint.getAllRepo(query ?: "architecture").execute()
            } catch (e: Exception) { // 通信自体が失敗した場合
                val exception = AppException.convertTo(e as Throwable)
                throw exception
            }

            if (response.isSuccessful) {
                val responseBody = response.body()
                val entityList = convertToEntity(responseBody?.items)
                entityList
            } else {
                val exception = GithubExceptionConverter.convertTo(
                    response.code(),
                    response.errorBody()?.string()
                )
                throw exception
            }
        }
    }

    private fun convertToEntity(repos: List<Repository>?): RepositoryListEntity? {
        return repos?.let { repos ->
            RepositoryListEntity(
                repos.map {
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
        } ?: null
    }
}