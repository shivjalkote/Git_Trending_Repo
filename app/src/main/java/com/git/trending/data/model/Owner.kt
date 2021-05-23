package com.git.trending.data.model

import com.google.gson.annotations.SerializedName


/**Created by Shiv Jalkote on 08-May-2021. **/

data class Owner(
    val id: Int,

    @SerializedName("login")
    val name: String?,

    @SerializedName("avatar_url")
    var avatarUrl: String?

)