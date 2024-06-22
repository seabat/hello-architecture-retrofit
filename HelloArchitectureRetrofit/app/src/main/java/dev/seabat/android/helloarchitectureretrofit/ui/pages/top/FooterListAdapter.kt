package dev.seabat.android.helloarchitectureretrofit.ui.pages.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.seabat.android.helloarchitectureretrofit.databinding.ListitemFooterGithubReopBinding

class FooterListAdapter(
    private val onPageClick: (page: Int) -> Unit
) : RecyclerView.Adapter<FooterListAdapter.FooterListViewHolder>() {

    var pageNumber = 0

    class FooterListViewHolder(val binding: ListitemFooterGithubReopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListitemFooterGithubReopBinding.inflate(layoutInflater)

        // NOTE: レイアウト側で width を match_parent に指定してもなぜか wrap_content になってしまうので、
        //       ここで width を match_parent に設定する。
        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return FooterListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: FooterListViewHolder, position: Int) {
        holder.bind()
    }
}