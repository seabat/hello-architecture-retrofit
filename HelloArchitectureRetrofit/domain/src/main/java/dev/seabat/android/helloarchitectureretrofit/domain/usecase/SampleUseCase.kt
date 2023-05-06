package dev.seabat.android.helloarchitectureretrofit.domain.usecase

import dev.seabat.android.helloarchitectureretrofit.domain.repository.SampleRepositoryContract

class SampleUseCase(val sampleRepository: SampleRepositoryContract) : SampleUseCaseContract {
    override fun loadSample(): String {
        return sampleRepository.fetchSample()
    }
    override fun saveSample(sample: String) {
        sampleRepository.updateSample(sample)
    }
}