package dev.seabat.android.helloarchitectureretrofit.domain.usecase

import dev.seabat.android.helloarchitectureretrofit.domain.entity.AllRepositoryEntity

interface GithubUseCaseContract {
    suspend fun loadRepos(query: String?, page: Int): AllRepositoryEntity?
}