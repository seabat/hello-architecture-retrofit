package dev.seabat.android.helloarchitectureretrofit.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.helloarchitectureretrofit.data.datasource.github.GithubApiService
import dev.seabat.android.helloarchitectureretrofit.data.repository.GithubRepository
import dev.seabat.android.helloarchitectureretrofit.domain.repository.GithubRepositoryContract
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

