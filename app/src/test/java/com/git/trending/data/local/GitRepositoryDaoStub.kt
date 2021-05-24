package com.git.trending.data.local

import com.git.trending.data.local.dao.GitRepositoryDao
import com.git.trending.data.local.entity.GitRepositoryEntity


/**Created by Shiv Jalkote on 21-May-2021. **/

class GitRepositoryDaoStub : GitRepositoryDao {

    private val mGitRepositoryEntityList = mutableListOf<GitRepositoryEntity>()

    private fun updateDataList(gitRepoEntity: GitRepositoryEntity) {
        for ((index, entity) in mGitRepositoryEntityList.iterator().withIndex()) {
            if (entity.id == gitRepoEntity.id) {
                mGitRepositoryEntityList.removeAt(index)
                mGitRepositoryEntityList.add(gitRepoEntity)
                break
            }
        }
    }

    private fun findRepoById(id: Int): GitRepositoryEntity? {
        for (entity: GitRepositoryEntity in mGitRepositoryEntityList) {
            if (entity.id == id)
                return entity
        }
        return null
    }

    private fun findRepoByName(name: String): List<GitRepositoryEntity> {
        val repoList = mutableListOf<GitRepositoryEntity>()
        for (entity: GitRepositoryEntity in mGitRepositoryEntityList) {
            if (entity.name?.contains(name, ignoreCase = true) == true)
                repoList.add(entity)
        }
        return repoList
    }


    override suspend fun insert(repo: GitRepositoryEntity): Long {
        repo ?: return 0
        mGitRepositoryEntityList.add(repo)
        return repo.id.toLong()

    }

    override suspend fun getRepositories(): List<GitRepositoryEntity> = mGitRepositoryEntityList

    override suspend fun update(gitRepo: GitRepositoryEntity) = updateDataList(gitRepo)

    override suspend fun getGitRepositoryById(id: Int): GitRepositoryEntity? = findRepoById(id)

    override suspend fun getRepositoryByName(name: String): List<GitRepositoryEntity> =
        findRepoByName(name)
}
