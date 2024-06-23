package dev.seabat.android.pagingarchitectureretrofit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubApiService
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubApi

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModuleModuleProvider {
    @Provides
    fun provideGithubApiEndpoint(
    ): GithubApiService {
        return GithubApi.githubApiService
    }
}
