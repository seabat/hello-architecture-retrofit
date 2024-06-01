package dev.seabat.android.pagingarchitectureretrofit.domain.repository

import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryListEntity

interface GithubRepositoryContract {
    suspend fun fetchRepos(query: String?): RepositoryListEntity?
}