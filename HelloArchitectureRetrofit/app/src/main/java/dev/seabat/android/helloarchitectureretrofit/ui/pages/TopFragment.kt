package dev.seabat.android.helloarchitectureretrofit.ui.pages

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import dev.seabat.android.helloarchitectureretrofit.R
import dev.seabat.android.helloarchitectureretrofit.data.repository.SampleRepository
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.SampleUseCase

class TopFragment : Fragment(R.layout.page_top) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.text_sample).text = SampleUseCase(SampleRepository()).loadSample()
        return
    }
}