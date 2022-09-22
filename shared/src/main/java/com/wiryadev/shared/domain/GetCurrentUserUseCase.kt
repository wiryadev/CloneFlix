package com.wiryadev.shared.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.shared.data.model.mapper.UserMapper
import com.wiryadev.shared.data.model.viewparam.UserViewParam
import com.wiryadev.shared.data.repository.UserPreferenceRepository
import com.wiryadev.shared.utils.ext.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCurrentUserUseCase(
    private val repository: UserPreferenceRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Nothing, UserViewParam>(dispatcher) {

    override suspend fun execute(param: Nothing?): Flow<ViewResource<UserViewParam>> {
        return flow {
            repository.getCurrentUser().collect { dataResource ->
                dataResource.suspendSubscribe(
                    doOnSuccess = { response ->
                        emit(ViewResource.Success(UserMapper.toViewParam(response.payload)))
                    },
                    doOnError = { error ->
                        emit(ViewResource.Error(error.exception))
                    }
                )
            }
        }
    }

}