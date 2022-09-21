package com.wiryadev.register.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.register.data.repository.RegisterRepository
import com.wiryadev.shared.data.model.mapper.UserMapper
import com.wiryadev.shared.data.model.viewparam.UserViewParam
import com.wiryadev.shared.domain.SaveAuthDataUseCase
import com.wiryadev.shared.utils.GenderUtils
import com.wiryadev.shared.utils.ext.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RegisterUserUseCase(
    private val checkRegisterFieldUseCase: CheckRegisterFieldUseCase,
    private val saveAuthDataUseCase: SaveAuthDataUseCase,
    private val repository: RegisterRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<RegisterUserUseCase.Param, UserViewParam>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<UserViewParam>> {
        return flow {
            emit(ViewResource.Loading())

            mutateGenderParam(param)?.let { p ->
                checkRegisterFieldUseCase(p).first().suspendSubscribe(
                    doOnSuccess = {
                        repository
                            .registerUser(
                                birthdate = p.birthdate,
                                email = p.email,
                                gender = p.gender,
                                password = p.password,
                                username = p.username,
                            )
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

    private fun mutateGenderParam(param: Param?): Param? {
        return param?.copy(
            gender = GenderUtils.parseGender(param.gender)
        )
    }

    data class Param(
        val birthdate: String,
        val email: String,
        val gender: String,
        val password: String,
        val username: String,
    )
}