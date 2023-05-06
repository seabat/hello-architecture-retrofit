package dev.seabat.android.helloarchitectureretrofit.ui.pages

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.SampleUseCaseContract

class TopViewModel(
    private val sampleUseCase: SampleUseCaseContract,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
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

    fun loadText(): String {
        return sampleUseCase.loadSample()
    }
}