package dev.seabat.android.pagingarchitectureretrofit.data.repository

import androidx.paging.PagingSource
import dev.seabat.android.pagingarchitectureretrofit.data.datasource.github.GithubApiService
import dev.seabat.android.pagingarchitectureretrofit.data.pagesource.RepositoryPagingSource
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.repository.GithubRepositoryContract

class GithubRepository(private val endpoint: GithubApiService) : GithubRepositoryContract {

    override fun repositoryPagingSource(query: String): PagingSource<Int, RepositoryEntity> {
        return RepositoryPagingSource(query, endpoint)
    }
}