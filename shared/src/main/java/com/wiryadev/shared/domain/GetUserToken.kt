package com.wiryadev.shared.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.shared.data.repository.UserPreferenceRepository
import com.wiryadev.shared.utils.ext.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetUserToken(
    private val repository: UserPreferenceRepository,
    private val dispatcher: CoroutineDispatcher,
) : BaseUseCase<Nothing, String>(dispatcher) {

    override suspend fun execute(param: Nothing?): Flow<ViewResource<String>> {
        return flow {
            repository.getUserToken().map {
                it.suspendSubscribe(
                    doOnSuccess = { result ->
                        emit(ViewResource.Success(data = result.payload.orEmpty()))
                    },
                    doOnError = { error ->
                        emit(ViewResource.Error(exception = error.exception))
                    }
                )
            }
        }
    }
}