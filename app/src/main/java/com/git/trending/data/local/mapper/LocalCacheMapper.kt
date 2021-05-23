package com.git.trending.data.local.mapper

import com.git.trending.data.local.entity.GitRepositoryEntity
import com.git.trending.data.model.GitRepository
import com.git.trending.data.model.Owner
import com.git.trending.util.EntityMapper
import javax.inject.Inject


/**Created by Shiv Jalkote on 08-May-2021. **/

open class LocalCacheMapper @Inject constructor() :
    EntityMapper<GitRepositoryEntity, GitRepository> {

    override fun mapFromEntity(entity: GitRepositoryEntity): GitRepository {

        return GitRepository(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            owner = Owner(
                id = entity.ownerId,
                name = entity.ownerName,
                avatarUrl = entity.avatarUrl
            ),
            starCount = entity.starCount,
            forks = entity.forks
        );
    }

    override fun mapToEntity(domainModel: GitRepository): GitRepositoryEntity {
        return GitRepositoryEntity(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            ownerId = domainModel.owner.id,
            ownerName = domainModel.owner.name,
            avatarUrl = domainModel.owner.avatarUrl,
            starCount = domainModel.starCount,
            forks = domainModel.forks
        )
    }

    fun mapFromEntityList(entityList: List<GitRepositoryEntity>): List<GitRepository> {
        val repoList = mutableListOf<GitRepository>()
        entityList.forEach { repoList.add(mapFromEntity(it)) }
        return repoList
    }


}