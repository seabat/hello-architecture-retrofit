package dev.seabat.android.helloarchitectureretrofit.data.repository

import com.google.gson.Gson
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model.GetAllRepoResponse
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GithubApiEndpoint
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GithubRetrofitClient
import dev.seabat.android.helloarchitectureretrofit.domain.repository.SampleRepositoryContract
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Response

class SampleRepository() : SampleRepositoryContract {

    // TODO: 最終的に依存注入する
    private val endpoint: GithubApiEndpoint = GithubRetrofitClient.createApiEndpoint()

    override suspend fun fetchSample(): String {
        return suspendCancellableCoroutine<String> { continuation ->
            val call = endpoint.getAllRepo("architecture")
            call.enqueue(object : retrofit2.Callback<GetAllRepoResponse> {
                override fun onFailure(call: Call<GetAllRepoResponse>, t: Throwable) {
                    continuation.resume("", null)
                }

                override fun onResponse(
                    call: Call<GetAllRepoResponse>,
                    response: Response<GetAllRepoResponse>
                ) {
                    if(response.isSuccessful) {
                        val getAllRepoResponse = response.body()
                        continuation.resume(Gson().run { toJson(getAllRepoResponse) }, null)
                    } else {
                        continuation.resume("", null)
                    }

                }
            })
        }
    }

    override fun updateSample(sample: String) {
        // Do nothing
    }
}