package com.git.trending.data.remote.response

import com.google.gson.annotations.SerializedName


/**Created by Shiv Jalkote on 08-May-2021. **/

data class OwnerResponse(
    val id: Int,
    val name: String?,

    @SerializedName("avatar_url")
    var avatarUrl: String?
)