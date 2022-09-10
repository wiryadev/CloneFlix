package com.wiryadev.splashscreen.data.network.service

import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.splashscreen.data.network.model.SyncResponse
import retrofit2.http.GET

interface SplashscreenService {

    @GET("api/v1/sync")
    suspend fun syncUser(): BaseResponse<SyncResponse>

}