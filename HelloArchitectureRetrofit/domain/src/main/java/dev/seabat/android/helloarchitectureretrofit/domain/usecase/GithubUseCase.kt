package dev.seabat.android.helloarchitectureretrofit.domain.usecase

import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryListEntity
import dev.seabat.android.helloarchitectureretrofit.domain.repository.GithubRepositoryContract

class GithubUseCase(val githubRepository: GithubRepositoryContract) : GithubUseCaseContract {
    override suspend fun loadRepos(query: String?): RepositoryListEntity? {
        return githubRepository.fetchRepos(query)
    }
}