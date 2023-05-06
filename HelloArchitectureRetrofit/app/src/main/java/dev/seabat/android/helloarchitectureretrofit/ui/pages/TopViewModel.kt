package dev.seabat.android.helloarchitectureretrofit.ui.pages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.SampleUseCaseContract
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val sampleUseCase: SampleUseCaseContract,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _textSample = MutableLiveData<String>("")
    val textSample: LiveData<String>
        get() = _textSample

    fun loadText() {
        viewModelScope.launch{
            _textSample.value = sampleUseCase.loadSample()
        }
    }
}