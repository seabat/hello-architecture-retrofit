package dev.seabat.android.helloarchitectureretrofit.ui.pages.top

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.seabat.android.helloarchitectureretrofit.databinding.ListitemFooterGithubReopBinding
import dev.seabat.android.helloarchitectureretrofit.domain.entity.RepositoryListEntity

class FooterListAdapter(
    private val onPageClick: (page: Int) -> Unit
) : RecyclerView.Adapter<FooterListAdapter.FooterListViewHolder>() {

    var totalPageNumber = 0
    var currentPageNumber = 0

    fun updatePageNumber(totalPageNumber: Int, currentPageNumber: Int) {
        this.totalPageNumber = totalPageNumber
        this.currentPageNumber = currentPageNumber
        this.notifyDataSetChanged()
    }

    class FooterListViewHolder(val binding: ListitemFooterGithubReopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(totalPageNumber: Int, currentPageNumber: Int, onPageClick: (page: Int) -> Unit) {
            // ー  1 / 100 ＋

            binding.imageBack.apply {
                visibility = if (2 <= currentPageNumber) {
                    VISIBLE
                } else {
                    INVISIBLE
                }
                setOnClickListener {
                    onPageClick(currentPageNumber - 1)
                }
            }

            binding.imageForward.apply {
                visibility = if (currentPageNumber < totalPageNumber) {
                    VISIBLE
                }  else {
                    INVISIBLE
                }
                setOnClickListener {
                    onPageClick(currentPageNumber + 1)
                }
            }

            binding.textCurrent.text = currentPageNumber.toString()
            binding.textTotal.text = totalPageNumber.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListitemFooterGithubReopBinding.inflate(layoutInflater, parent, false)
        return FooterListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: FooterListViewHolder, position: Int) {
        holder.bind(totalPageNumber, currentPageNumber, onPageClick)
    }
}