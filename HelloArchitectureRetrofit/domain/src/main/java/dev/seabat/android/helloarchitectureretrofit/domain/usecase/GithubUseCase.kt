package dev.seabat.android.helloarchitectureretrofit.domain.usecase

import dev.seabat.android.helloarchitectureretrofit.domain.entity.AllRepositoryEntity
import dev.seabat.android.helloarchitectureretrofit.domain.repository.GithubRepositoryContract

class GithubUseCase(
    private val githubRepository: GithubRepositoryContract,
) : GithubUseCaseContract {
    override suspend fun loadRepos(query: String?, page: Int): AllRepositoryEntity? {
        return githubRepository.fetchRepos(query, page)
    }
}