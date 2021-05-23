package com.git.trending.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.git.trending.data.model.GitRepository
import com.git.trending.data.repository.IGitRepoRepository
import com.git.trending.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


/**Created by Shiv Jalkote on 09-May-2021. **/

@HiltViewModel
class GitRepositoryViewModel @Inject constructor(
    private val gitRepoRepository: IGitRepoRepository,

    ) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<GitRepository>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<GitRepository>>>
        get() = _dataState

    private val _dataStateRepository: MutableLiveData<DataState<GitRepository>> = MutableLiveData()
    val dataStateRepository: LiveData<DataState<GitRepository>>
        get() = _dataStateRepository


    fun fetchRepositories() {
        viewModelScope.launch {
            gitRepoRepository.getRemoteGitRepositories()
                .onEach { dataState ->
                    _dataState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun fetchRepository(ownerName: String, repoName: String) {
        viewModelScope.launch {
            gitRepoRepository.getRemoteGitRepository(ownerName, repoName)
                .onEach { dataState ->
                    _dataStateRepository.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun fetchLocalRepository(id: Int) {
        viewModelScope.launch {
            val gitRepository = gitRepoRepository.getLocalRepositoryById(id)
            _dataStateRepository.value = DataState.Success(gitRepository)
        }
    }

    fun fetchLocalRepositories(name: String = "") {
        viewModelScope.launch {
            val gitRepository = gitRepoRepository.getLocalRepositoriesByName(name)
            _dataState.value = DataState.Success(gitRepository)
        }
    }


    fun getRepositoryCount(): Int {
        return if (_dataState.value is DataState.Success)
            (_dataState.value as DataState.Success<List<GitRepository>>).data?.size ?: 0 else 0
    }

}

