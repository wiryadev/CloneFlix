package com.wiryadev.register.data.network.service

import com.wiryadev.register.data.network.model.RegisterRequest
import com.wiryadev.shared.data.model.response.AuthResponse
import com.wiryadev.shared.data.model.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("/api/v1/user/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): BaseResponse<AuthResponse>

}