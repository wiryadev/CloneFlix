package com.wiryadev.detailmovie.data.network.service

import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.model.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailMovieService {

    @GET("api/v1/movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): BaseResponse<MovieResponse>

}