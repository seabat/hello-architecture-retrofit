package dev.seabat.android.pagingarchitectureretrofit.ui.pages.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.seabat.android.pagingarchitectureretrofit.R
import dev.seabat.android.pagingarchitectureretrofit.databinding.ListitemGithubRepoBinding
import dev.seabat.android.pagingarchitectureretrofit.domain.entity.RepositoryEntity

class RepositoryListAdapter(
    private val onListItemClick: (fullName: String, htmlUrl: String) -> Unit
) : PagingDataAdapter<RepositoryEntity, RepositoryListAdapter.RepositoryViewHolder>(
    REPOSITORY_DIFF_CALLBACK
) {
    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
            holder.setClickListener(tile, onListItemClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            ListitemGithubRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    class RepositoryViewHolder(private val binding: ListitemGithubRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RepositoryEntity) {
            binding.textName.text = data.name
            binding.textDesc.text = data.description ?: ""
            binding.textCreatedDate.text = data.createdAt

            Glide.with(binding.imageThubm)
                .load(data.owner.avatarUrl)
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher_foreground)
                .error(R.mipmap.ic_launcher_foreground)
                .fallback(R.mipmap.ic_launcher_foreground)
                .into(binding.imageThubm)
        }

        fun setClickListener(
            data: RepositoryEntity,
            onListItemClick: (fullName: String, htmlUrl: String) -> Unit
        ) {
            binding.layoutRoot.setOnClickListener {
                onListItemClick(data.fullName, data.htmlUrl)
            }
        }
    }

    companion object {
        private val REPOSITORY_DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepositoryEntity>() {
            override fun areItemsTheSame(
                oldItem: RepositoryEntity,
                newItem: RepositoryEntity
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RepositoryEntity,
                newItem: RepositoryEntity
            ): Boolean = oldItem == newItem
        }
    }
}