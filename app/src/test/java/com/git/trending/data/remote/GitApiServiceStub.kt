package com.git.trending.data.remote

import com.git.trending.data.model.Owner
import com.git.trending.data.remote.api.GitApiService
import com.git.trending.data.remote.response.GitRepositoryResponse
import retrofit2.http.Path
import kotlin.random.Random


/**Created by Shiv Jalkote on 21-May-2021. **/

class GitApiServiceStub {

    private fun getFakeListResponse(): List<GitRepositoryResponse> {
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

    suspend fun getGitRepository(): List<GitRepositoryResponse> = getFakeListResponse()

    suspend fun getGitRepository(
        ownerName: String,
        repoName: String
    ): GitRepositoryResponse = createNewRepoResponse(Random.nextInt(), repoName, ownerName)

    suspend fun getRepo1(): GitRepositoryResponse {
        return createNewRepoResponse(Random.nextInt())
    }


}
