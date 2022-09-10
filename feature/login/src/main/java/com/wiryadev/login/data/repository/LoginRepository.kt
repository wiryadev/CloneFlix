package com.wiryadev.login.data.repository

import com.wiryadev.core.wrapper.DataResource
import com.wiryadev.login.data.network.datasource.LoginDataSource
import com.wiryadev.login.data.network.model.LoginRequest
import com.wiryadev.shared.data.model.response.AuthResponse
import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias LoginDataResource = DataResource<BaseResponse<AuthResponse>>

interface LoginRepository {

    suspend fun loginUser(
        email: String,
        password: String,
    ): Flow<LoginDataResource>

}

class LoginRepositoryImpl(
    private val dataSource: LoginDataSource
) : Repository(), LoginRepository {

    override suspend fun loginUser(email: String, password: String): Flow<LoginDataResource> {
        return flow {
            emit(safeNetworkCall {
                dataSource.loginUser(LoginRequest(email, password))
            })
        }
    }
}