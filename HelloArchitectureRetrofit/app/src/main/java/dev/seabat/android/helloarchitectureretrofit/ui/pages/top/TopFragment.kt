package dev.seabat.android.helloarchitectureretrofit.ui.pages.top

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.helloarchitectureretrofit.R
import dev.seabat.android.helloarchitectureretrofit.databinding.PageTopBinding
import dev.seabat.android.helloarchitectureretrofit.ui.dialog.showSimpleErrorDialog

@AndroidEntryPoint
class TopFragment : Fragment(R.layout.page_top) {
    companion object {
        val TAG: String = TopFragment::class.java.simpleName
    }

    private var binding: PageTopBinding? = null
    private val viewModel: TopViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PageTopBinding.bind(view)
        initAppBar()
        initView()
        initObserver()
        viewModel.loadRepositories()
        return
    }

    private fun initView() {
        // RecycleView に Adapter を設定
        binding?.recyclerview?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            adapter = ConcatAdapter(
                RepositoryListAdapter(onListItemClick = this@TopFragment.onListItemClick),
                FooterListAdapter {}
            )
        }

        // SearchView のクローズリスナー
        binding?.search?.setOnCloseListener {
            binding?.search?.visibility = View.GONE
            binding?.toolbar?.visibility = View.VISIBLE
            true
        }

        // SearchView の入力リスナー
        binding?.search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.loadRepositories(query)
                binding?.search?.visibility = View.GONE
                binding?.toolbar?.visibility = View.VISIBLE
                return false
            }
        })
    }

    private fun initObserver() {
        // リストの更新
        viewModel.repositories.observe(viewLifecycleOwner) {
            val concatAdapter = binding?.recyclerview?.adapter as? ConcatAdapter
            (concatAdapter?.adapters?.get(0) as? RepositoryListAdapter)?.updateRepositoryList(it)
        }

        // プログレスバーの表示・非表示の切り替え
        viewModel.progressVisible.observe(viewLifecycleOwner) {
            if (it) {
                binding?.progressbar?.visibility = View.VISIBLE
            } else {
                binding?.progressbar?.visibility = View.GONE
            }
        }

        // エラーダイアログの表示
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showSimpleErrorDialog(
                    message = it,
                    requestKey = TAG,
                    requestBundle = bundleOf("errorMessage" to it),
                    onClickCallback = { key, bundle ->
                        if (key == TAG) {
                            android.util.Log.d(
                                "Hello",
                                "Error dialog closed(${bundle.getString("errorMessage")})"
                            )
                            viewModel.clearError()
                        }
                    }
                )
            }
        }
    }

    private fun initAppBar() {
        // NOTE: フラグメントが所有するアプリバーは onCreateOptionsMenu ではなく
        //       ここで onViewCreated 等で inflate する
        //       ref. https://developer.android.com/guide/fragments/appbar?hl=ja#fragment-inflate
        binding?.toolbar?.inflateMenu(R.menu.top)
        binding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    binding?.search?.visibility = View.VISIBLE
                    binding?.toolbar?.visibility = View.GONE
                    true
                }

                R.id.menu_refresh -> {
                    viewModel.loadRepositories()
                    true
                }

                else -> false
            }
        }
    }

    private val onListItemClick: (fullName: String, htmlUrl: String) -> Unit =
        { fullName, htmlUrl ->
            // 詳細画面に遷移
            val action = TopFragmentDirections.actionToRepoDetail().apply {
                repoName = fullName
                repoUrl = htmlUrl
            }
            this.findNavController().navigate(action)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}