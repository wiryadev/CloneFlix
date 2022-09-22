package com.wiryadev.home.data.repository

import com.wiryadev.core.wrapper.DataResource
import com.wiryadev.home.data.network.datasource.HomeDataSource
import com.wiryadev.home.data.network.model.HomeFeedResponse
import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.model.response.MovieResponse
import com.wiryadev.shared.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias HomeDataResource = DataResource<BaseResponse<HomeFeedResponse>>
typealias WatchlistDataResource = DataResource<BaseResponse<List<MovieResponse>>>

interface HomeRepository {

    suspend fun fetchHomeFeeds(): Flow<HomeDataResource>

    suspend fun fetchWatchlist(): Flow<WatchlistDataResource>

}

class HomeRepositoryImpl(
    private val dataSource: HomeDataSource
) : Repository(), HomeRepository {

    override suspend fun fetchHomeFeeds(): Flow<HomeDataResource> = flow {
        emit(safeNetworkCall {
            dataSource.fetchHomeFeeds()
        })
    }

    override suspend fun fetchWatchlist(): Flow<WatchlistDataResource> = flow {
        emit(safeNetworkCall {
            dataSource.fetchWatchlist()
        })
    }

}