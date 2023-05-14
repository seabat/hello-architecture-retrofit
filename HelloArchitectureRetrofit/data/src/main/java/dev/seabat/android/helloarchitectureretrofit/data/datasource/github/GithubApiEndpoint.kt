package dev.seabat.android.helloarchitectureretrofit.data.datasource.github

import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.model.GetAllRepoResponse
import dev.seabat.android.helloarchitectureretrofit.domain.exception.ErrorType
import dev.seabat.android.helloarchitectureretrofit.domain.exception.HelloException
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiEndpoint {
    @GET("search/repositories")
    fun getAllRepo(@Query("q") q : String): Call<GetAllRepoResponse>
}

