package com.wiryadev.login.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.login.data.repository.LoginRepository
import com.wiryadev.shared.data.model.mapper.UserMapper
import com.wiryadev.shared.data.model.viewparam.UserViewParam
import com.wiryadev.shared.domain.SaveAuthDataUseCase
import com.wiryadev.shared.utils.ext.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class LoginUserUseCase(
    private val checkLoginFieldUseCase: CheckLoginFieldUseCase,
    private val saveAuthDataUseCase: SaveAuthDataUseCase,
    private val repository: LoginRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<LoginUserUseCase.Param, UserViewParam>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<UserViewParam>> {
        return flow {
            emit(ViewResource.Loading())

            param?.let {
                checkLoginFieldUseCase(param).first().suspendSubscribe(
                    doOnSuccess = {
                        repository.loginUser(param.email, param.password)
                            .first()
                            .suspendSubscribe(
                                doOnSuccess = { loginResult ->
                                    val result = loginResult.payload?.data
                                    val token = result?.token
                                    val user = result?.user

                                    if (!token.isNullOrEmpty() && user != null) {
                                        saveAuthDataUseCase(
                                            SaveAuthDataUseCase.Param(
                                                isLoggedIn = true,
                                                authToken = token,
                                                user = user,
                                            )
                                        ).first().suspendSubscribe(
                                            doOnSuccess = {
                                                emit(
                                                    ViewResource.Success(
                                                        UserMapper.toViewParam(user)
                                                    )
                                                )
                                            },
                                            doOnError = { error ->
                                                emit(ViewResource.Error(error.exception))
                                            }
                                        )
                                    }
                                },
                                doOnError = { error ->
                                    emit(ViewResource.Error(error.exception))
                                },
                            )
                    },
                    doOnError = { error ->
                        emit(ViewResource.Error(error.exception))
                    }
                )
            } ?: throw IllegalStateException("Param Required")
        }
    }

    data class Param(
        val email: String,
        val password: String,
    )
}