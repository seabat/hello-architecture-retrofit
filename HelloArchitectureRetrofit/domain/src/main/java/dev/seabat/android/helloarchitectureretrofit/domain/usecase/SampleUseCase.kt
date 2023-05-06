package dev.seabat.android.helloarchitectureretrofit.domain.usecase

class SampleUseCase : SampleUseCaseContract {
    override fun loadSample(): String {
        return "xyz"
    }
    override fun saveSample(sample: String) {}
}