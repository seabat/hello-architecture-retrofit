package dev.seabat.android.helloarchitectureretrofit.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.helloarchitectureretrofit.domain.repository.SampleRepositoryContract
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.SampleUseCase
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.SampleUseCaseContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModuleBinder {
    @Binds
    @Singleton
    abstract fun bindSampleUseCaseContract(
        sampleUseCase: SampleUseCase
    ): SampleUseCaseContract
}


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModuleProvider {
    @Provides
    fun provideSampleUseCase(
        sampleRepository: SampleRepositoryContract
    ): SampleUseCase {
        return SampleUseCase(sampleRepository)
    }
}
