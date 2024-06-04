package dev.seabat.android.pagingarchitectureretrofit.ui.pages.top

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.pagingarchitectureretrofit.ErrorStringConverter
import dev.seabat.android.pagingarchitectureretrofit.R
import dev.seabat.android.pagingarchitectureretrofit.databinding.PageTopBinding
import dev.seabat.android.pagingarchitectureretrofit.domain.exception.AppException
import dev.seabat.android.pagingarchitectureretrofit.ui.dialog.showSimpleErrorDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        return
    }

    private fun initView() {
        // RecycleView に Adapter を設定
        val repositoryListAdapter = RepositoryListAdapter(onListItemClick = this@TopFragment.onListItemClick)
        binding?.recyclerview?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            adapter = repositoryListAdapter

            lifecycleScope.launch {
                // We repeat on the STARTED lifecycle because an Activity may be PAUSED
                // but still visible on the screen, for example in a multi window app
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.pagingDataFlow.collectLatest {
                        repositoryListAdapter.submitData(it)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repositoryListAdapter.loadStateFlow.collectLatest { states ->
                when (val state = states.refresh) {
                    is LoadState.Loading -> {
                        Log.d("PAR_PAGING", "Loading")
                        binding?.progressbar?.visibility = View.VISIBLE
                    }
                    is LoadState.Error -> {
                        Log.d("PAR_PAGING", "Error")
                        val errorString = ErrorStringConverter.convertTo((state.error as AppException).errType)
                        showSimpleErrorDialog(
                            message =errorString,
                            requestKey = TAG,
                            requestBundle = bundleOf("errorMessage" to errorString),
                            onClickCallback = { key, bundle ->
                                if (key == TAG) {
                                    Log.d(
                                        "PAR_PAGING",
                                        "Error dialog closed(${bundle.getString("errorMessage")})"
                                    )
                                }
                            }
                        )
                    }
                    is LoadState.NotLoading -> {
                        binding?.progressbar?.visibility = View.GONE
                        binding?.recyclerview?.scrollToPosition(0)
                        Log.d("PAR_PAGING", "NotLoading")
                    }
                }
            }
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
                query?.let {
                    viewModel.updateQuery(it)
                }
                binding?.search?.visibility = View.GONE
                binding?.toolbar?.visibility = View.VISIBLE
                return false
            }
        })
    }

    private fun initObserver() {
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
                    viewModel.updateQuery()
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