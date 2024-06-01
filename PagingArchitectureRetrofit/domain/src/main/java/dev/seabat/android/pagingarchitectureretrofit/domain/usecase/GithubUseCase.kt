package dev.seabat.android.pagingarchitectureretrofit.domain.usecase

import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryListEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.repository.GithubRepositoryContract

class GithubUseCase(val githubRepository: GithubRepositoryContract) : GithubUseCaseContract {
    override suspend fun loadRepos(query: String?): RepositoryListEntity? {
        return githubRepository.fetchRepos(query)
    }
}