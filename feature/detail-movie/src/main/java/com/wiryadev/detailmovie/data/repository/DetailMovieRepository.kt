package com.wiryadev.detailmovie.data.repository

import com.wiryadev.core.wrapper.DataResource
import com.wiryadev.detailmovie.data.network.datasource.DetailMovieDataSource
import com.wiryadev.shared.data.model.response.BaseResponse
import com.wiryadev.shared.data.model.response.MovieResponse
import com.wiryadev.shared.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias DetailMovieDataResource = DataResource<BaseResponse<MovieResponse>>

interface DetailMovieRepository {

    suspend fun getMovieDetail(movieId: Int): Flow<DetailMovieDataResource>

}

class DetailMovieRepositoryImpl(
    private val dataSource: DetailMovieDataSource
): Repository(), DetailMovieRepository {

    override suspend fun getMovieDetail(movieId: Int): Flow<DetailMovieDataResource> {
        return flow {
            emit(safeNetworkCall {
                dataSource.getMovieDetail(movieId)
            })
        }
    }

}