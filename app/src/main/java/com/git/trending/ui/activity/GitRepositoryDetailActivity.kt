package com.git.trending.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.git.trending.R
import com.git.trending.base.BaseActivity
import com.git.trending.data.model.GitRepository
import com.git.trending.databinding.ActivityRepositoryDetailBinding
import com.git.trending.util.AndroidUtil
import com.git.trending.util.DataState
import com.git.trending.viewmodel.GitRepositoryViewModel
import dagger.hilt.android.AndroidEntryPoint

/**Created by Shiv Jalkote on 09-May-2021. **/

@AndroidEntryPoint
class GitRepositoryDetailActivity : BaseActivity() {

    private lateinit var mBinding: ActivityRepositoryDetailBinding
    private lateinit var mOwnerName: String
    private lateinit var mRepositoryName: String
    private lateinit var mRepositoryId: Integer
    private val mGitRepositoryViewModel: GitRepositoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRepositoryDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.toolbar)
        getIntentData()
        subscribeObserver()
        setLocalDataOnView()
        fetRepositoryData()
    }

    private fun getIntentData() {
        val bundle = intent.extras ?: return
        mRepositoryName = bundle.getString("REPOSITORY_NAME").toString()
        mRepositoryId = bundle.getInt("REPOSITORY_ID") as Integer
        mOwnerName = bundle.getString("REPOSITORY_OWNER_NAME").toString()
    }

    private fun setLocalDataOnView() {
        mGitRepositoryViewModel.fetchLocalRepository(mRepositoryId.toInt())
    }

    private fun setDataToView(gitRepoModel: GitRepository) {
        mBinding.gitRepository = gitRepoModel
    }

    private fun showProgressBar() {
        mBinding.progressBarLoading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        mBinding.progressBarLoading.visibility = View.GONE
    }

    private fun subscribeObserver() {
        mGitRepositoryViewModel.dataStateRepository.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<GitRepository> -> {
                    hideProgressBar()
                    dataState.data ?: return@observe
                    setDataToView(dataState.data)
                }

                is DataState.Error -> {
                    val displayMessage =
                        dataState.message ?: getString(R.string.error_unknown_error)
                    Toast.makeText(this, displayMessage, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }

                is DataState.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun fetRepositoryData() {
        if (AndroidUtil.isNetworkAvailable(this))
            mGitRepositoryViewModel.fetchRepository(
                mOwnerName,
                mRepositoryName
            )
    }

    override fun onResume() {
        super.onResume()
        setActionBarTitle(mRepositoryName, true)
    }

}