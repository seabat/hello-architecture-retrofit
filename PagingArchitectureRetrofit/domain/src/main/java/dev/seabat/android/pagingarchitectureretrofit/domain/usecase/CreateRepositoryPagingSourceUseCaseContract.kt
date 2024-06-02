package dev.seabat.android.pagingarchitectureretrofit.domain.usecase

import androidx.paging.PagingData
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryEntity
import kotlinx.coroutines.flow.Flow

interface CreateRepositoryPagingSourceUseCaseContract {
    fun invoke(query: String?): Flow<PagingData<RepositoryEntity>>
}