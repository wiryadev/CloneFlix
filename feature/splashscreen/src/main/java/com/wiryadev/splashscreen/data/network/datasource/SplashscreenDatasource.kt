package com.wiryadev.splashscreen.data.network.datasource

import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.splashscreen.data.network.model.SyncResponse
import com.wiryadev.splashscreen.data.network.service.SplashscreenService

interface SplashscreenDatasource {

    suspend fun syncUser(): BaseResponse<SyncResponse>

}

class SplashscreenDatasourceImpl(
    private val service: SplashscreenService
) : SplashscreenDatasource {
    override suspend fun syncUser(): BaseResponse<SyncResponse> {
        return service.syncUser()
    }
}