package com.git.trending.data.remote.mapper

import com.git.trending.data.model.GitRepository
import com.git.trending.data.remote.response.GitRepositoryResponse
import com.git.trending.util.EntityMapper
import javax.inject.Inject


/**Created by Shiv Jalkote on 08-May-2021. **/

class ApiResponseMapper @Inject constructor() :
    EntityMapper<GitRepositoryResponse, GitRepository> {

    override fun mapFromEntity(entity: GitRepositoryResponse): GitRepository {
        return GitRepository(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            owner = entity.owner,
            starCount = entity.starCount,
            forks = entity.forks
        )
    }

    override fun mapToEntity(domainModel: GitRepository): GitRepositoryResponse {
        return GitRepositoryResponse(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            owner = domainModel.owner,
            starCount = domainModel.starCount,
            forks = domainModel.forks
        )
    }


}