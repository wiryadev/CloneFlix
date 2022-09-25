package com.wiryadev.detailmovie.data.network.datasource

import com.wiryadev.detailmovie.data.network.service.DetailMovieService
import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.model.response.MovieResponse

interface DetailMovieDataSource {

    suspend fun getMovieDetail(movieId: Int): BaseResponse<MovieResponse>

}

class DetailMovieDataSourceImpl(private val service: DetailMovieService) : DetailMovieDataSource {

    override suspend fun getMovieDetail(movieId: Int): BaseResponse<MovieResponse> {
        return service.getMovieDetail(movieId)
    }

}