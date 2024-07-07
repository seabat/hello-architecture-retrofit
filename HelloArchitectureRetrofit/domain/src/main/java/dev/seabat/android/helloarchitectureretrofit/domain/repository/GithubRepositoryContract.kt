package dev.seabat.android.helloarchitectureretrofit.domain.repository

import dev.seabat.android.helloarchitectureretrofit.domain.entity.AllRepositoryEntity

interface GithubRepositoryContract {
    suspend fun fetchRepos(query: String?, page: Int): AllRepositoryEntity?
}