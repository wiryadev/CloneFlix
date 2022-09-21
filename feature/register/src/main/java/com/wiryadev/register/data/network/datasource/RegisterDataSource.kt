package com.wiryadev.register.data.network.datasource

import com.wiryadev.register.data.network.model.RegisterRequest
import com.wiryadev.register.data.network.service.RegisterService
import com.wiryadev.shared.data.model.response.AuthResponse
import com.wiryadev.shared.data.model.response.BaseResponse

interface RegisterDataSource {

    suspend fun registerUser(registerRequest: RegisterRequest): BaseResponse<AuthResponse>

}

class RegisterDataSourceImpl(
    private val service: RegisterService
) : RegisterDataSource {

    override suspend fun registerUser(registerRequest: RegisterRequest): BaseResponse<AuthResponse> {
        return service.registerUser(registerRequest)
    }

}