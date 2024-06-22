package dev.seabat.android.pagingarchitectureretrofit.ui.pages.top

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.pagingarchitectureretrofit.domain.usecase.CreateRepositoryPagingSourceUseCaseContract
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

private const val DEFAULT_QUERY = "architecture"

@HiltViewModel
class TopViewModel @Inject constructor(
    private val createRepositoryPagingSourceUseCase: CreateRepositoryPagingSourceUseCaseContract,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val queryLiveData = MutableLiveData(DEFAULT_QUERY)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow = queryLiveData.asFlow()
        .flatMapLatest {
            // NOTE: queryLiveData が更新されると flatMapLatest のラムダ式が起動する
            createRepositoryPagingSourceUseCase.invoke(it)
        }
        .cachedIn(viewModelScope)

    fun updateQuery(query: String = queryLiveData.value ?: DEFAULT_QUERY) {
        queryLiveData.value = query
    }
}