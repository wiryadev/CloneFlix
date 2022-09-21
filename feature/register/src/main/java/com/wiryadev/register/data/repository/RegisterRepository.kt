package com.wiryadev.register.data.repository

import com.wiryadev.core.wrapper.DataResource
import com.wiryadev.register.data.network.datasource.RegisterDataSource
import com.wiryadev.register.data.network.model.RegisterRequest
import com.wiryadev.shared.data.model.response.AuthResponse
import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias RegisterDataResource = DataResource<BaseResponse<AuthResponse>>

interface RegisterRepository {

    suspend fun registerUser(
        birthdate: String,
        email: String,
        gender: String,
        password: String,
        username: String,
    ): Flow<RegisterDataResource>

}

class RegisterRepositoryImpl(
    private val dataSource: RegisterDataSource
) : Repository(), RegisterRepository {

    override suspend fun registerUser(
        birthdate: String,
        email: String,
        gender: String,
        password: String,
        username: String
    ): Flow<RegisterDataResource> = flow {
        emit(
            safeNetworkCall {
                dataSource.registerUser(
                    RegisterRequest(
                        birthdate = birthdate,
                        email = email,
                        gender = gender,
                        password = password,
                        username = username
                    )
                )
            }
        )
    }
}