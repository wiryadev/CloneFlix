package com.wiryadev.shared.data.repository

import com.wiryadev.core.wrapper.DataResource
import com.wiryadev.shared.data.model.request.WatchlistRequest
import com.wiryadev.shared.data.remote.service.SharedFeatureService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SharedApiRepository {

    suspend fun addWatchlist(movieId: String): Flow<DataResource<Any>>

    suspend fun removeWatchlist(movieId: String): Flow<DataResource<Any>>

}

class SharedApiRepositoryImpl(
    private val apiService: SharedFeatureService,
) : Repository(), SharedApiRepository {

    override suspend fun addWatchlist(movieId: String): Flow<DataResource<Any>> {
        return flow {
            emit(safeNetworkCall { apiService.addWatchlist(WatchlistRequest(movieId)) })
        }
    }

    override suspend fun removeWatchlist(movieId: String): Flow<DataResource<Any>> {
        return flow {
            emit(safeNetworkCall { apiService.removeWatchlist(WatchlistRequest(movieId)) })
        }
    }
}