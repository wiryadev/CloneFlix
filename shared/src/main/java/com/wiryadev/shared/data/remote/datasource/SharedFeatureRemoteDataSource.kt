package com.wiryadev.shared.data.remote.datasource

import com.wiryadev.shared.data.model.request.WatchlistRequest
import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.remote.service.SharedFeatureService

interface SharedFeatureRemoteDataSource {

    suspend fun addWatchlist(request: WatchlistRequest): BaseResponse<Any>

    suspend fun removeWatchlist(request: WatchlistRequest): BaseResponse<Any>

}

class SharedFeatureRemoteDataSourceImpl(
    private val sharedFeatureService: SharedFeatureService
) : SharedFeatureRemoteDataSource {

    override suspend fun addWatchlist(request: WatchlistRequest): BaseResponse<Any> {
        return sharedFeatureService.addWatchlist(request)
    }

    override suspend fun removeWatchlist(request: WatchlistRequest): BaseResponse<Any> {
        return sharedFeatureService.removeWatchlist(request)
    }
}