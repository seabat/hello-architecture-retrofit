package dev.seabat.android.helloarchitectureretrofit.domain.repository

interface SampleRepositoryContract {
    fun fetchSample(): String
    fun updateSample(sample: String)
}