package dev.seabat.android.helloarchitectureretrofit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GithubApi
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GithubApiService

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModuleProvider {
    @Provides
    fun provideGithubApiEndpoint(): GithubApiService {
        return GithubApi.githubApiService
    }
}
