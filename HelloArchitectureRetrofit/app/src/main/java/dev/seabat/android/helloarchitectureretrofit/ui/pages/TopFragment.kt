package dev.seabat.android.helloarchitectureretrofit.ui.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.helloarchitectureretrofit.R
import dev.seabat.android.helloarchitectureretrofit.databinding.PageTopBinding

@AndroidEntryPoint
class TopFragment : Fragment(R.layout.page_top) {
    private var binding: PageTopBinding? = null
    private val viewModel: TopViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageTopBinding.bind(view)
        viewModel.textSample.observe(viewLifecycleOwner) {
            binding?.textSample?.text = it
        }
        viewModel.loadText()
        return
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}