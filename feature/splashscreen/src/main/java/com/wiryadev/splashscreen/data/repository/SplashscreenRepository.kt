package com.wiryadev.splashscreen.data.repository

import com.wiryadev.core.wrapper.DataResource
import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.repository.Repository
import com.wiryadev.splashscreen.data.network.datasource.SplashscreenDatasource
import com.wiryadev.splashscreen.data.network.model.SyncResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SplashscreenRepository {

    suspend fun syncUser(): Flow<DataResource<BaseResponse<SyncResponse>>>

}

class SplashscreenRepositoryImpl(
    private val datasource: SplashscreenDatasource,
) : Repository(), SplashscreenRepository {

    override suspend fun syncUser(): Flow<DataResource<BaseResponse<SyncResponse>>> {
        return flow {
            emit(safeNetworkCall { datasource.syncUser() })
        }
    }

}