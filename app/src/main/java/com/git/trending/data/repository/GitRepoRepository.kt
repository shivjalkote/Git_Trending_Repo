package com.git.trending.data.repository

import com.git.trending.data.local.dao.GitRepositoryDao
import com.git.trending.data.local.mapper.LocalCacheMapper
import com.git.trending.data.model.GitRepository
import com.git.trending.data.remote.api.GitApiService
import com.git.trending.data.remote.mapper.ApiResponseMapper
import com.git.trending.data.remote.response.GitRepositoryResponse
import com.git.trending.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**Created by Shiv Jalkote on 08-May-2021. **/

class GitRepoRepository(
    private val gitRepoDao: GitRepositoryDao,
    private val apiService: GitApiService,
    private val cacheMapper: LocalCacheMapper,
    private val responseMapper: ApiResponseMapper
) : IGitRepoRepository {

    suspend fun getLis():Flow<DataState<List<GitRepositoryResponse>>> = flow {

        emit( DataState.Success(apiService.getGitRepository()))
    }

    override suspend fun getRemoteGitRepositories(): Flow<DataState<List<GitRepository>>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getGitRepository()
            response.forEach {
                val gitRepoEntity =
                    cacheMapper.mapToEntity(responseMapper.mapFromEntity(it))
                gitRepoDao.insert(gitRepoEntity)
            }
            val cachedRepository = gitRepoDao.getRepositories()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedRepository)))

        } catch (e: Exception) {
            emit(DataState.Error(e, e.message))
        }
    }

    override suspend fun getRemoteGitRepository(
        ownerName: String,
        repoName: String
    ): Flow<DataState<GitRepository>> = flow {
        emit(DataState.Loading)
        try {
            val gitRepositoryEntity = cacheMapper.mapToEntity(
                responseMapper.mapFromEntity(
                    apiService.getGitRepository(
                        ownerName,
                        repoName
                    )
                )
            )
            gitRepoDao.update(gitRepositoryEntity)
            emit(DataState.Success(cacheMapper.mapFromEntity(gitRepositoryEntity)))
        } catch (e: Exception) {
            emit(DataState.Error(e, e.message))
        }
    }

    override suspend fun getLocalRepositoriesByName(name: String): List<GitRepository> {
        val repoList = gitRepoDao.getRepositoryByName(name)
        return cacheMapper.mapFromEntityList(repoList)
    }

    override suspend fun getLocalRepositoryById(id: Int): GitRepository? {
        val gitRepoEntity = gitRepoDao.getGitRepositoryById(id) ?: return null
        return cacheMapper.mapFromEntity(gitRepoEntity)
    }

}