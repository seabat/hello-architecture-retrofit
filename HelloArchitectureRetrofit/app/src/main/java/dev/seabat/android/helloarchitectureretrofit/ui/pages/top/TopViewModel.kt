package dev.seabat.android.helloarchitectureretrofit.ui.pages.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.helloarchitectureretrofit.ErrorStringConverter
import dev.seabat.android.helloarchitectureretrofit.domain.entity.AllRepositoryEntity
import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryListEntity
import dev.seabat.android.helloarchitectureretrofit.domain.exception.HelloException
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.GithubUseCaseContract
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val githubUseCase: GithubUseCaseContract,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _repositories = MutableLiveData<AllRepositoryEntity>(
        AllRepositoryEntity(
            0,
            0,
            0,
            RepositoryListEntity(arrayListOf())
        )
    )
    val repositories: LiveData<AllRepositoryEntity>
        get() = _repositories

    private var _progressVisible = MutableLiveData<Boolean>(false)
    val progressVisible: LiveData<Boolean>
        get() = _progressVisible

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private var cachedQuery: String = "architecture"

    fun loadRepositories(query: String? = cachedQuery, page: Int = 1) {
        query?.let {
            cachedQuery = it
        }
        viewModelScope.launch {
            _progressVisible.value = true
            kotlin.runCatching {
                githubUseCase.loadRepos(
                    cachedQuery, page
                ) ?: AllRepositoryEntity(
                    0,
                    0,
                    0,
                    RepositoryListEntity(arrayListOf())
                )
            }.onSuccess { repositories ->
                _repositories.value = repositories
            }.onFailure {
                val errorString = ErrorStringConverter.convertTo((it as HelloException).errType)
                android.util.Log.d("Hello", errorString)
                _errorMessage.value = errorString
            }.also {
                _progressVisible.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}