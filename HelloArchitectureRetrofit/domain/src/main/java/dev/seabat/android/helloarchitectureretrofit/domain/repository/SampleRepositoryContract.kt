package dev.seabat.android.helloarchitectureretrofit.domain.repository

interface SampleRepositoryContract {
    suspend fun fetchSample(): String
    fun updateSample(sample: String)
}