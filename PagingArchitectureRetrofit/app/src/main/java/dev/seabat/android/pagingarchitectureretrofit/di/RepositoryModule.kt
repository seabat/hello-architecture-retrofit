package dev.seabat.android.pagingarchitectureretrofit.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubApiService
import dev.seabat.android.pagingarchitectureretrofit.data.repository.GithubRepository
import dev.seabat.android.pagingarchitectureretrofit.domain.repository.GithubRepositoryContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinder {
    @Binds
    @Singleton
    abstract fun bindGithubRepositoryContract(
        gitHubRepository: GithubRepository
    ): GithubRepositoryContract
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModuleProvider {
    @Provides
    fun provideGithubRepository(
        endpoint: GithubApiService
    ): GithubRepository {
        return GithubRepository(endpoint)
    }
}

