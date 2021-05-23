package com.git.trending.data.remote.response

import com.git.trending.data.model.Owner
import com.google.gson.annotations.SerializedName


/**Created by Shiv Jalkote on 08-May-2021. **/

data class GitRepositoryResponse(
   val id: Int,
   val name: String?,
   val description: String?,
   val owner: Owner,
   @SerializedName("stargazers_count")
   val starCount: Int?,
   val forks: Int?
)
