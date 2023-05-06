package dev.seabat.android.helloarchitectureretrofit.domain.usecase

interface SampleUseCaseContract {
    fun loadSample(): String
    fun saveSample(sample: String)
}