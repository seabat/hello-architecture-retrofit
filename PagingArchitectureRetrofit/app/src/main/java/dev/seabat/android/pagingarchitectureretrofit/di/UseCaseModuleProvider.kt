package dev.seabat.android.pagingarchitectureretrofit.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.pagingarchitectureretrofit.domain.repository.GithubRepositoryContract
import dev.seabat.android.pagingarchitectureretrofit.domain.usecase.CreateRepositoryPagingSourceUseCase
import dev.seabat.android.pagingarchitectureretrofit.domain.usecase.CreateRepositoryPagingSourceUseCaseContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModuleBinder {
    @Binds
    @Singleton
    abstract fun bindGithubUseCaseContract(
        githubUseCase: CreateRepositoryPagingSourceUseCase
    ): CreateRepositoryPagingSourceUseCaseContract
}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModuleProvider {
    @Provides
    fun provideGithubUseCase(
        githubRepository: GithubRepositoryContract
    ): CreateRepositoryPagingSourceUseCase {
        return CreateRepositoryPagingSourceUseCase(githubRepository)
    }
}
