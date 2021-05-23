package com.git.trending.data.repository

import com.git.trending.data.model.GitRepository
import com.git.trending.util.DataState
import kotlinx.coroutines.flow.Flow


/**Created by Shiv Jalkote on 11-May-2021. **/

interface IGitRepoRepository {

    suspend fun getRemoteGitRepositories(): Flow<DataState<List<GitRepository>>>
    suspend fun getRemoteGitRepository(
        ownerName: String,
        repoName: String
    ): Flow<DataState<GitRepository>>

    suspend fun getLocalRepositoriesByName(name: String):List<GitRepository>

    suspend fun getLocalRepositoryById(id: Int): GitRepository?

}