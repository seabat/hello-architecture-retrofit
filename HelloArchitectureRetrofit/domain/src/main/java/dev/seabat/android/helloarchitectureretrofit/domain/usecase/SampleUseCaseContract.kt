package dev.seabat.android.helloarchitectureretrofit.domain.usecase

interface SampleUseCaseContract {
    suspend fun loadSample(): String
    suspend fun saveSample(sample: String)
}