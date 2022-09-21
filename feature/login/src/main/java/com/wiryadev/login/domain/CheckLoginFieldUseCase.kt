package com.wiryadev.login.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.exception.FieldErrorException
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.login.R
import com.wiryadev.login.constans.LoginFieldConstants.FIELD_EMAIL
import com.wiryadev.login.constans.LoginFieldConstants.FIELD_PASSWORD
import com.wiryadev.shared.utils.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias CheckFieldLoginResult = List<Pair<Int, Int>>

class CheckLoginFieldUseCase(
    dispatcher: CoroutineDispatcher
) : BaseUseCase<LoginUserUseCase.Param, CheckFieldLoginResult>(dispatcher) {

    override suspend fun execute(
        param: LoginUserUseCase.Param?
    ): Flow<ViewResource<CheckFieldLoginResult>> {
        return flow {
            param?.let {
                val result = mutableListOf<Pair<Int, Int>>()
                checkIsEmailValid(param.email)?.let {
                    result.add(it)
                }
                checkIsPasswordValid(param.password)?.let {
                    result.add(it)
                }
                if (result.isEmpty()) {
                    emit(ViewResource.Success(result))
                } else {
                    emit(ViewResource.Error(FieldErrorException(result), result))
                }
            } ?: throw IllegalStateException("Param Required")
        }
    }

    private fun checkIsPasswordValid(password: String): Pair<Int, Int>? {
        return if (password.isEmpty()) {
            Pair(FIELD_PASSWORD, R.string.error_field_password)
        } else {
            null
        }
    }

    private fun checkIsEmailValid(email: String): Pair<Int, Int>? {
        return if (email.isEmpty()) {
            Pair(FIELD_EMAIL, R.string.error_field_password)
        } else if (!StringUtils.isEmailValid(email)) {
            Pair(FIELD_EMAIL, R.string.error_field_email_invalid)
        } else {
            null
        }
    }

}