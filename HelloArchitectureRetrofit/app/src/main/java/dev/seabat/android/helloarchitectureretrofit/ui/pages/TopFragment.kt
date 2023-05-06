package dev.seabat.android.helloarchitectureretrofit.ui.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dev.seabat.android.helloarchitectureretrofit.R
import dev.seabat.android.helloarchitectureretrofit.data.repository.SampleRepository
import dev.seabat.android.helloarchitectureretrofit.databinding.PageTopBinding
import dev.seabat.android.helloarchitectureretrofit.domain.usecase.SampleUseCase

class TopFragment : Fragment(R.layout.page_top) {
    private var binding: PageTopBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageTopBinding.bind(view)
        binding?.textSample?.text = SampleUseCase(SampleRepository()).loadSample()
        return
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}