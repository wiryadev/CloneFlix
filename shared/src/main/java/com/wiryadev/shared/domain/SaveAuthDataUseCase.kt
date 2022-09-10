package com.wiryadev.shared.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.DataResource
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.shared.data.model.response.UserResponse
import com.wiryadev.shared.data.repository.UserPreferenceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class SaveAuthDataUseCase(
    private val repository: UserPreferenceRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<SaveAuthDataUseCase.Param, Boolean>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<Boolean>> {
        return flow {
            param?.let {
                val saveLoginStatus = repository.updateUserLoginStatus(it.isLoggedIn).first()
                val saveToken = repository.updateUserToken(it.authToken).first()
                val saveUser = repository.setCurrentUser(it.user).first()

                if (saveUser is DataResource.Success
                    && saveToken is DataResource.Success
                    && saveLoginStatus is DataResource.Success
                ) {
                    emit(ViewResource.Success(true))
                } else {
                    emit(ViewResource.Error(IllegalStateException("Failed to save local data")))
                }
            }
        }
    }

    data class Param(
        val isLoggedIn: Boolean,
        val authToken: String,
        val user: UserResponse,
    )
}