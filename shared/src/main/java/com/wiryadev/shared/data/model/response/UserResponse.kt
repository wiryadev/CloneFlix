package com.wiryadev.shared.data.model.response


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("gender")
    val gender: Int?,
)