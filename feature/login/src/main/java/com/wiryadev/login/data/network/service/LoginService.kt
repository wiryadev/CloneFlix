package com.wiryadev.login.data.network.service

import com.wiryadev.login.data.network.model.LoginRequest
import com.wiryadev.shared.data.model.response.AuthResponse
import com.wiryadev.shared.data.model.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/api/v1/user/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): BaseResponse<AuthResponse>

}