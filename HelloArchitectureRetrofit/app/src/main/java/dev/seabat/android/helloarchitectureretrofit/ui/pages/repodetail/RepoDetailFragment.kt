package dev.seabat.android.helloarchitectureretrofit.ui.pages.repodetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.helloarchitectureretrofit.R
import dev.seabat.android.helloarchitectureretrofit.databinding.PageRepoDetailBinding

@AndroidEntryPoint
class RepoDetailFragment : Fragment(R.layout.page_repo_detail) {
    companion object {
        val TAG: String = RepoDetailFragment::class.java.simpleName
    }

    private var binding: PageRepoDetailBinding? = null
    private val viewModel: RepoDetailViewModel by viewModels()
    val args: RepoDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageRepoDetailBinding.bind(view)
        initView()
        initObserver()
        return
    }

    private fun initView() {
        val repoUrl = args.repoUrl
        val repoWebView = binding?.webview
        repoWebView?.loadUrl(repoUrl)
    }

    private fun initObserver() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}