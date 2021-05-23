package com.git.trending.data.repository

import com.git.trending.data.local.entity.GitRepositoryEntity
import com.git.trending.data.local.mapper.LocalCacheMapper
import com.git.trending.data.model.GitRepository
import com.git.trending.data.model.Owner
import com.git.trending.data.remote.mapper.ApiResponseMapper
import com.git.trending.data.remote.response.GitRepositoryResponse
import com.git.trending.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


/**Created by Shiv Jalkote on 11-May-2021. **/

@RunWith(MockitoJUnitRunner::class)
class FakeGitRepoRepository(
    private val cacheMapper: LocalCacheMapper,
    private val responseMapper: ApiResponseMapper
) : IGitRepoRepository {

    private val gitRepositoryEntityList = mutableListOf<GitRepositoryEntity>()

    private fun getFakeApiResponse(): List<GitRepositoryResponse> {
        val responseList = mutableListOf<GitRepositoryResponse>()
        for (i in 1..5) {
            responseList.add(createNewRepoResponse(i))
        }
        return responseList
    }

    private fun createNewRepoResponse(
        id: Int,
        repoName: String = "Repo $id",
        ownerName: String = "Owner $id"
    ):
            GitRepositoryResponse {
        return GitRepositoryResponse(
            id,
            repoName,
            "Description for Repository $repoName",
            Owner(id, ownerName, null),
            id,
            id
        )
    }

    private fun getRepoResponseByName(ownerName: String, repoName: String): GitRepositoryResponse {
        val localRepo = findLocalRepoByNameAndOwner(ownerName, repoName)
        val id = localRepo?.id ?: gitRepositoryEntityList.size + 1
        return createNewRepoResponse(id, repoName, ownerName)
    }

    private fun findLocalRepoByNameAndOwner(ownerName: String, repoName: String): GitRepository? {
        for (entity: GitRepositoryEntity in gitRepositoryEntityList) {
            if (entity.ownerName == ownerName && entity.name == repoName)
                return cacheMapper.mapFromEntity(entity)
        }
        return null
    }

    private fun updateDataList(gitRepoEntity: GitRepositoryEntity) {
        for ((index, entity) in gitRepositoryEntityList.iterator().withIndex()) {
            if (entity.id == gitRepoEntity.id) {
                gitRepositoryEntityList.removeAt(index)
                gitRepositoryEntityList.add(gitRepoEntity)
                break
            }
        }
    }

    private fun findRepoById(id: Int): GitRepository? {
        for (entity: GitRepositoryEntity in gitRepositoryEntityList) {
            if (entity.id == id)
                return cacheMapper.mapFromEntity(entity)
        }
        return null
    }

    private fun findRepoByName(name: String): List<GitRepository> {
        val repoList = mutableListOf<GitRepository>()
        for (entity: GitRepositoryEntity in gitRepositoryEntityList) {
            if (entity.name?.contains(name, ignoreCase = true) == true)
                repoList.add(cacheMapper.mapFromEntity(entity))
        }
        return repoList
    }

    override suspend fun getRemoteGitRepositories(): Flow<DataState<List<GitRepository>>> = flow {
        emit(DataState.Loading)
        val response = getFakeApiResponse()
        try {
            response.forEach {
                val gitRepoEntity =
                    cacheMapper.mapToEntity(responseMapper.mapFromEntity(it))
                gitRepositoryEntityList.add(gitRepoEntity)
            }
            val list = cacheMapper.mapFromEntityList(gitRepositoryEntityList)
            val value = DataState.Success(list)
            emit(value)

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
                    getRepoResponseByName(ownerName, repoName)
                )
            )
            updateDataList(gitRepositoryEntity)
            emit(DataState.Success(cacheMapper.mapFromEntity(gitRepositoryEntity)))
        } catch (e: Exception) {
            emit(DataState.Error(e, e.message))
        }
    }


    override suspend fun getLocalRepositoriesByName(name: String): List<GitRepository> {
        return findRepoByName(name)
    }

    override suspend fun getLocalRepositoryById(id: Int): GitRepository? {
        return findRepoById(id)
    }

}