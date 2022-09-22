package com.wiryadev.home.data.network.service

import com.wiryadev.home.data.network.model.HomeFeedResponse
import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.model.response.MovieResponse
import retrofit2.http.GET

interface HomeService {

    @GET("api/v1/homepage")
    suspend fun fetchHomeFeeds(): BaseResponse<HomeFeedResponse>

    @GET("api/v1/watchlist")
    suspend fun fetchWatchlist(): BaseResponse<List<MovieResponse>>

}