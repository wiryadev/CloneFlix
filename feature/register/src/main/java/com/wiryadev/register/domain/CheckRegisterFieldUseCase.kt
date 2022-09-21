package com.wiryadev.register.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.exception.FieldErrorException
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.register.R
import com.wiryadev.register.constants.RegisterFieldConstants.FIELD_BIRTH_DATE
import com.wiryadev.register.constants.RegisterFieldConstants.FIELD_EMAIL
import com.wiryadev.register.constants.RegisterFieldConstants.FIELD_GENDER
import com.wiryadev.register.constants.RegisterFieldConstants.FIELD_PASSWORD
import com.wiryadev.register.constants.RegisterFieldConstants.FIELD_USERNAME
import com.wiryadev.shared.utils.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias CheckFieldRegisterResult = List<Pair<Int, Int>>

class CheckRegisterFieldUseCase(
    dispatcher: CoroutineDispatcher
) : BaseUseCase<RegisterUserUseCase.Param, CheckFieldRegisterResult>(dispatcher) {

    override suspend fun execute(
        param: RegisterUserUseCase.Param?
    ): Flow<ViewResource<CheckFieldRegisterResult>> {
        return flow {
            param?.let {
                val result = mutableListOf<Pair<Int, Int>>()
                checkIsEmailValid(param.email)?.let {
                    result.add(it)
                }
                checkIsPasswordValid(param.password)?.let {
                    result.add(it)
                }
                checkIsBirthdateValid(param.birthdate)?.let {
                    result.add(it)
                }
                checkIsUsernameValid(param.username)?.let {
                    result.add(it)
                }
                checkIsGenderValid(param.gender)?.let {
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
        } else if (password.length < 8) {
            Pair(FIELD_PASSWORD, R.string.error_field_username_length)
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

    private fun checkIsUsernameValid(username: String): Pair<Int, Int>? {
        return if (username.isEmpty()) {
            Pair(FIELD_USERNAME, R.string.error_field_empty)
        } else if (username.length < 8) {
            Pair(FIELD_USERNAME, R.string.error_field_username_length)
        } else if (username.contains(" ")) {
            Pair(FIELD_USERNAME, R.string.error_field_username_whitespace)
        } else {
            null
        }
    }

    private fun checkIsGenderValid(gender: String): Pair<Int, Int>? {
        return if (gender.isEmpty()) {
            Pair(FIELD_GENDER, R.string.error_field_empty)
        } else {
            null
        }
    }

    private fun checkIsBirthdateValid(birthdate: String): Pair<Int, Int>? {
        return if (birthdate.isEmpty()) {
            Pair(FIELD_BIRTH_DATE, R.string.error_field_empty)
        } else {
            null
        }
    }
}