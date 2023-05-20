package dev.seabat.android.helloarchitectureretrofit.domain.repository

import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryListEntity

interface GithubRepositoryContract {
    suspend fun fetchRepos(): RepositoryListEntity?
}