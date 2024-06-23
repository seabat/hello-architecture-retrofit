package dev.seabat.android.pagingarchitectureretrofit.domain.repository

import androidx.paging.PagingSource
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryEntity

interface GithubRepositoryContract {
    fun repositoryPagingSource(query: String): PagingSource<Int, RepositoryEntity>
}