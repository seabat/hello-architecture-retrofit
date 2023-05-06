package dev.seabat.android.helloarchitectureretrofit.data.repository

import dev.seabat.android.helloarchitectureretrofit.domain.repository.SampleRepositoryContract

class SampleRepository : SampleRepositoryContract {
    override fun fetchSample(): String {
        return "xyz"
    }

    override fun updateSample(sample: String) {
        // Do nothing
    }
}