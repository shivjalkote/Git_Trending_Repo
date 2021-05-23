package com.git.trending.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.git.trending.data.model.GitRepository
import com.git.trending.databinding.RowGitRepositoryBinding
import com.git.trending.ui.activity.GitRepositoryDetailActivity
import javax.inject.Inject


/**Created by Shiv Jalkote on 09-May-2021. **/

class GitRepositoryAdapter @Inject constructor() :
    RecyclerView.Adapter<GitRepositoryAdapter.RepositoryHolder>() {

    var mRepoList: MutableList<GitRepository> = mutableListOf()

    fun addAll(repoList: List<GitRepository>) {
        val startPosition = mRepoList.size
        mRepoList.addAll(repoList)
        notifyItemRangeInserted(startPosition, mRepoList.size)
    }

    fun replace(repoList: List<GitRepository>) {
        clear()
        addAll(repoList)
    }

    fun clear() {
        mRepoList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        return RepositoryHolder(
            RowGitRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val gitRepository = mRepoList[position]
        holder.binding.gitRepository = gitRepository
        holder.bind(gitRepository)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return mRepoList.size
    }

    class RepositoryHolder(val binding: RowGitRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gitRepository: GitRepository) {
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, GitRepositoryDetailActivity::class.java)
                intent.putExtra("REPOSITORY_ID", gitRepository.id)
                intent.putExtra("REPOSITORY_NAME", gitRepository.name)
                intent.putExtra("REPOSITORY_OWNER_NAME", gitRepository.owner.name)
                itemView.context.startActivity(intent)
            }
        }
    }
}