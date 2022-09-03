package com.wiryadev.splashscreen.data.network.model

import com.google.gson.annotations.SerializedName
import com.wiryadev.shared.data.model.response.UserResponse

data class SyncResponse(
    @SerializedName("user")
    val userResponse: UserResponse?
)
