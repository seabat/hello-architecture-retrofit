package dev.seabat.android.helloarchitectureretrofit.data.datasource.github

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GithubRetrofitClient {
    const val baseUrl = "https://api.github.com/"

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun createApiEndpoint(): GithubApiEndpoint {
        return retrofit.create(GithubApiEndpoint::class.java)
    }
}