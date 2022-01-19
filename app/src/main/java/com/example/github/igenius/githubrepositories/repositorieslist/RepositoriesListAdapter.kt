package com.example.github.igenius.githubrepositories.repositorieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.github.igenius.databinding.ItRepositoryBinding

class RepositoriesListAdapter(private val clickListener: RepoListener): ListAdapter<RepositoryDataItem, RepoViewHolder>(RepoDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    //Add companion object to inflate ViewHolder (from)
    companion object {
        fun from(parent: ViewGroup): RepoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItRepositoryBinding.inflate(layoutInflater, parent, false)

            return RepoViewHolder(binding)
        }
    }
}

class RepoViewHolder(private val binding: ItRepositoryBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: RepoListener, item: RepositoryDataItem) {
        binding.item = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}

//Create RepositoryDataItemDiffCallback
class RepoDiffCallback : DiffUtil.ItemCallback<RepositoryDataItem>() {
    override fun areItemsTheSame(oldItem: RepositoryDataItem, newItem: RepositoryDataItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: RepositoryDataItem, newItem: RepositoryDataItem): Boolean {
        return oldItem == newItem
    }
}


class RepoListener(val clickListener: (repositoryDataItem: RepositoryDataItem) -> Unit){
    fun onClick(repositoryDataItem: RepositoryDataItem) = clickListener(repositoryDataItem)
}