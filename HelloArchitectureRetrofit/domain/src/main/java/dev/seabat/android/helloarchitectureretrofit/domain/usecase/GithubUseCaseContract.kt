package dev.seabat.android.helloarchitectureretrofit.domain.usecase

import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryListEntity

interface GithubUseCaseContract {
    suspend fun loadRepos(): RepositoryListEntity?
}