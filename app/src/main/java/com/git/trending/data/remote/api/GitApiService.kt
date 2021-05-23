package com.git.trending.data.remote.api

import com.git.trending.data.model.GitRepository
import com.git.trending.data.remote.response.GitRepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path


/**Created by Shiv Jalkote on 08-May-2021. **/

interface GitApiService {

    @GET("repositories")
    suspend fun getGitRepository(): List<GitRepositoryResponse>

    @GET("repos/{ownerName}/{repoName}")
    suspend fun getGitRepository(
        @Path("ownerName") ownerName: String,
        @Path("repoName") repoName: String
    ):GitRepositoryResponse

    suspend fun getRepo1(): GitRepositoryResponse


}