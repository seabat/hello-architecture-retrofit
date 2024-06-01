package dev.seabat.android.pagingarchitectureretrofit.domain.usecase

import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryListEntity

interface GithubUseCaseContract {
    suspend fun loadRepos(query: String?): RepositoryListEntity?
}