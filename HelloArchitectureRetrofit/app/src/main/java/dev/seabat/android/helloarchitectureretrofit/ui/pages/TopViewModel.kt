package dev.seabat.android.helloarchitectureretrofit.ui.pages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.SampleUseCaseContract
import kotlinx.coroutines.launch

class TopViewModel(
    private val sampleUseCase: SampleUseCaseContract,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _textSample = MutableLiveData<String>("")
    val textSample: LiveData<String>
        get() = _textSample

    companion object {
        val EXTRA_REPOSITORY = object : CreationExtras.Key<SampleUseCaseContract> {}
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val savedStateHandle = extras.createSavedStateHandle()
                val scoreRepository = extras[EXTRA_REPOSITORY]!!
                return TopViewModel(scoreRepository, savedStateHandle) as T
            }
        }
    }

    fun loadText() {
        viewModelScope.launch{
            _textSample.value = sampleUseCase.loadSample()
        }
    }
}