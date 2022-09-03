package com.wiryadev.splashscreen.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.shared.data.model.mapper.UserMapper
import com.wiryadev.shared.data.model.viewparam.UserViewParam
import com.wiryadev.shared.data.repository.UserPreferenceRepository
import com.wiryadev.shared.utils.ext.suspendSubscribe
import com.wiryadev.splashscreen.data.repository.SplashscreenRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

typealias SyncResult = Pair<Boolean, UserViewParam?>

class SyncUserUseCase(
    private val splashscreenRepository: SplashscreenRepository,
    private val userPreferenceRepository: UserPreferenceRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Nothing, SyncResult>(dispatcher) {

    override suspend fun execute(param: Nothing?): Flow<ViewResource<SyncResult>> {
        return flow {
            userPreferenceRepository.isUserLoggedIn().first().suspendSubscribe(
                doOnSuccess = { result ->
                    if (result.payload == true) {
                        splashscreenRepository.syncUser().collect {
                            it.suspendSubscribe(
                                doOnSuccess = { response ->
                                    emit(
                                        ViewResource.Success(
                                            Pair(
                                                true,
                                                UserMapper.toViewParam(response.payload?.data?.userResponse)
                                            )
                                        )
                                    )
                                },
                                doOnError = { error ->
                                    emit(ViewResource.Error(error.exception))
                                }
                            )
                        }
                    } else {
                        emit(
                            ViewResource.Success(Pair(false, null))
                        )
                    }
                },
                doOnError = { error ->
                    emit(ViewResource.Error(error.exception))
                }
            )
        }
    }

}