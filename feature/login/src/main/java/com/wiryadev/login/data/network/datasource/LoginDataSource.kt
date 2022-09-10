package com.wiryadev.login.data.network.datasource

import com.wiryadev.login.data.network.model.LoginRequest
import com.wiryadev.login.data.network.service.LoginService
import com.wiryadev.shared.data.model.response.AuthResponse
import com.wiryadev.shared.data.model.response.BaseResponse

interface LoginDataSource {

    suspend fun loginUser(loginRequest: LoginRequest): BaseResponse<AuthResponse>

}

class LoginDataSourceImpl(private val service: LoginService) : LoginDataSource {

    override suspend fun loginUser(loginRequest: LoginRequest): BaseResponse<AuthResponse> {
        return service.loginUser(loginRequest)
    }

}