package dev.seabat.android.helloarchitectureretrofit.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.helloarchitectureretrofit.data.repository.SampleRepository
import dev.seabat.android.helloarchitectureretrofit.domain.repository.SampleRepositoryContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinder {
    @Binds
    @Singleton
    abstract fun bindSampleRepositoryContract(
        sampleRepository: SampleRepository
    ): SampleRepositoryContract
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModuleProvider {
    @Provides
    fun provideSampleRepository(
    ): SampleRepository {
        return SampleRepository()
    }
}

