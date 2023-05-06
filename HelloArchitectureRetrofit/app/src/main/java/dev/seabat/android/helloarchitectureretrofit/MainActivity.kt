package dev.seabat.android.helloarchitectureretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.seabat.android.helloarchitectureretrofit.data.repository.SampleRepository
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.SampleUseCase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SampleUseCase(SampleRepository()).loadSample()
    }
}