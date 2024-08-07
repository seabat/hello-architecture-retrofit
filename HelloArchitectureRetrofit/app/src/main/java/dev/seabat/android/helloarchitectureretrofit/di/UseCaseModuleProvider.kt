package dev.seabat.android.helloarchitectureretrofit.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.helloarchitectureretrofit.domain.repository.GithubRepositoryContract
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.GithubUseCase
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.GithubUseCaseContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModuleBinder {
    @Binds
    @Singleton
    abstract fun bindGithubUseCaseContract(
        githubUseCase: GithubUseCase,
    ): GithubUseCaseContract
}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModuleProvider {
    @Provides
    fun provideGithubUseCase(
        githubRepository: GithubRepositoryContract,
    ): GithubUseCase {
        return GithubUseCase(githubRepository)
    }
}
