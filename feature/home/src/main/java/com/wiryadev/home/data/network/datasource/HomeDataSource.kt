package com.wiryadev.home.data.network.datasource

import com.wiryadev.home.data.network.model.HomeFeedResponse
import com.wiryadev.home.data.network.service.HomeService
import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.model.response.MovieResponse

interface HomeDataSource {

    suspend fun fetchHomeFeeds(): BaseResponse<HomeFeedResponse>

    suspend fun fetchWatchlist(): BaseResponse<List<MovieResponse>>
}

class HomeDataSourceImpl(private val service: HomeService) : HomeDataSource {

    override suspend fun fetchHomeFeeds(): BaseResponse<HomeFeedResponse> {
        return service.fetchHomeFeeds()
    }

    override suspend fun fetchWatchlist(): BaseResponse<List<MovieResponse>> {
        return service.fetchWatchlist()
    }


}