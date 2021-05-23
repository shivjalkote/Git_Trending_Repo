package com.git.trending.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.git.trending.R
import com.git.trending.base.BaseActivity
import com.git.trending.data.model.GitRepository
import com.git.trending.databinding.ActivityGitRepositoryListBinding
import com.git.trending.ui.adapter.GitRepositoryAdapter
import com.git.trending.util.AndroidUtil
import com.git.trending.util.DataState
import com.git.trending.viewmodel.GitRepositoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GitRepositoryListActivity : BaseActivity() {

    private val mViewModel: GitRepositoryViewModel by viewModels()
    private lateinit var mBinding: ActivityGitRepositoryListBinding

    @Inject
    lateinit var mRepositoryAdapter: GitRepositoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityGitRepositoryListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        subscribeObserver()
        initActivityView()
    }

    private fun initActivityView() {
        mBinding.recycleViewRepository.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mRepositoryAdapter
        }

        mBinding.searchViewRepository.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mViewModel.fetchLocalRepositories(newText)
                return false
            }
        })

        if (TextUtils.isEmpty(mBinding.searchViewRepository.query) && mViewModel.getRepositoryCount() == 0) {
            if (AndroidUtil.isNetworkAvailable(this))
                mViewModel.fetchRepositories()
            else {
                mViewModel.fetchLocalRepositories()
            }
        }

    }

    private fun showProgressBar() {
        mBinding.progressBarLoading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        mBinding.progressBarLoading.visibility = View.GONE
    }

    private fun subscribeObserver() {
        mViewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<List<GitRepository>> -> {
                    hideProgressBar()
                    val resultantRepoList=dataState.data?: mutableListOf()
                    showResultOnDisplay(resultantRepoList)
                }

                is DataState.Error -> {
                    val displayMessage = dataState.message ?: getString(R.string.error_unknown_error)
                    Toast.makeText(this, displayMessage, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                    mRepositoryAdapter.clear()
                }

                is DataState.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showResultOnDisplay(gitRepositories: List<GitRepository>) {
        mRepositoryAdapter.replace(gitRepositories)
        mBinding.textViewNoData.visibility = View.GONE

        if (gitRepositories.isEmpty() && !TextUtils.isEmpty(mBinding.searchViewRepository.query)) {
            mBinding.textViewNoData.visibility = View.VISIBLE
            mBinding.textViewNoData.text =
                "${getString(R.string.msg_repo_not_found)} ${mBinding.searchViewRepository.query}"
        } else if (gitRepositories.isEmpty() && AndroidUtil.isNetworkAvailable(this)) {
            AndroidUtil.showToastMessage(
                this,
                getString(R.string.msg_check_internet_connection),
                Toast.LENGTH_SHORT
            )
        }


    }


}