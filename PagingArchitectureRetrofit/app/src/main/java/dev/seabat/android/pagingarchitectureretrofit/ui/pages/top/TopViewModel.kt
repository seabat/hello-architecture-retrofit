package dev.seabat.android.pagingarchitectureretrofit.ui.pages.top

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryEntity
import dev.seabat.android.pagingarchitectureretrofit.domain.usecase.CreateRepositoryPagingSourceUseCaseContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val createRepositoryPagingSourceUseCase: CreateRepositoryPagingSourceUseCaseContract,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var cachedQuery: String = "architecture"

    fun loadRepositories(query: String? = null): Flow<PagingData<RepositoryEntity>> {
        query?.let {
            cachedQuery = it
        }

        return createRepositoryPagingSourceUseCase.invoke(cachedQuery)
            .cachedIn(viewModelScope)
    }
}