package com.git.trending.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**Created by Shiv Jalkote on 08-May-2021. **/

@Entity
data class GitRepositoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val name: String?,
    val description: String?,

    val ownerId: Int,
    var ownerName: String?,
    val avatarUrl: String?,
    val starCount: Int?,
    val forks: Int?
)