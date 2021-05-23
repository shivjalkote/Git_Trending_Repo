package com.git.trending.data.model


/**Created by Shiv Jalkote on 08-May-2021. **/

data class GitRepository(
    val id: Int,
    val name: String?,
    val description: String?,
    val owner: Owner,
    val starCount: Int?,
    val forks: Int?
)