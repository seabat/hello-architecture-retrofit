package dev.seabat.android.helloarchitectureretrofit.data.repository

import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model.GetAllRepoResponse
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GithubApiEndpoint
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model.Repository
import dev.seabat.android.helloarchitectureretrofit.domain.entity.OwnerEntity
import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryEntity
import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryListEntity
import dev.seabat.android.helloarchitectureretrofit.domain.repository.GithubRepositoryContract
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Response

class GithubRepository(private val endpoint: GithubApiEndpoint) : GithubRepositoryContract {

    override suspend fun fetchRepos(): RepositoryListEntity? {
        return suspendCancellableCoroutine<RepositoryListEntity?> { continuation ->
            val call = endpoint.getAllRepo("architecture")
            call.enqueue(object : retrofit2.Callback<GetAllRepoResponse> {
                override fun onFailure(call: Call<GetAllRepoResponse>, t: Throwable) {
                    continuation.resume(null, null)
                }

                override fun onResponse(
                    call: Call<GetAllRepoResponse>,
                    response: Response<GetAllRepoResponse>
                ) {
                    if(response.isSuccessful) {
                        val getAllRepoResponse = response.body()
                        val entityList = convertToEntity(getAllRepoResponse?.items)
                        continuation.resume(entityList, null)
                    } else {
                        continuation.resume(null, null)
                    }

                }
            })
        }
    }

    private fun convertToEntity(repos: ArrayList<Repository>?): RepositoryListEntity? {
        return repos?.let { repos ->
            RepositoryListEntity(
                repos.map {
                    RepositoryEntity(
                        name = it.name,
                        description = it.description,
                        created_at = it.created_at,
                        owner = OwnerEntity( avatar_url = it.owner.avatar_url)
                    )
                } as ArrayList<RepositoryEntity>
            )
        } ?: null
    }
}