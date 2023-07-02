package dev.seabat.android.helloarchitectureretrofit.data.repository

import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GitHubExceptionConverter
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model.GetAllRepoResponse
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GithubApiEndpoint
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model.Repository
import dev.seabat.android.helloarchitectureretrofit.domain.entity.OwnerEntity
import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryEntity
import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryListEntity
import dev.seabat.android.helloarchitectureretrofit.domain.exception.HelloException
import dev.seabat.android.helloarchitectureretrofit.domain.repository.GithubRepositoryContract
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resumeWithException

class GithubRepository(private val endpoint: GithubApiEndpoint) : GithubRepositoryContract {

    override suspend fun fetchRepos(query: String?): RepositoryListEntity? {
        return suspendCancellableCoroutine<RepositoryListEntity?> { continuation ->
            val call = endpoint.getAllRepo(query ?: "architecture")
            call.enqueue(object : retrofit2.Callback<GetAllRepoResponse> {
                override fun onFailure(call: Call<GetAllRepoResponse>, t: Throwable) {
                    continuation.resumeWithException(HelloException.convertTo(t))
                }
                override fun onResponse(
                    call: Call<GetAllRepoResponse>,
                    response: Response<GetAllRepoResponse>
                ) {
                    if (response.isSuccessful) {
                        val getAllRepoResponse = response.body()
                        val entityList = convertToEntity(getAllRepoResponse?.items)
                        continuation.resume(entityList, null)
                    } else {
                        continuation.resumeWithException(GitHubExceptionConverter.convertTo(
                            response.code(),
                            response.errorBody()?.string()
                        ))
                    }
                }
            })
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
                        owner = OwnerEntity( avatarUrl = it.owner.avatarUrl)
                    )
                } as ArrayList<RepositoryEntity>
            )
        } ?: null
    }
}