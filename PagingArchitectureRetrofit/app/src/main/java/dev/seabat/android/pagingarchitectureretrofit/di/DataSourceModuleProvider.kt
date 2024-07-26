package dev.seabat.android.pagingarchitectureretrofit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubApi
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubApiService

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModuleProvider {
    @Provides
    fun provideGithubApiEndpoint(): GithubApiService {
        return GithubApi.githubApiService
    }
}
