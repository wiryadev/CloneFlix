package com.wiryadev.detailmovie.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.detailmovie.data.repository.DetailMovieRepository
import com.wiryadev.shared.data.model.mapper.MovieMapper
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.utils.ext.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDetailMovieUseCase(
    private val repository: DetailMovieRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, MovieViewParam>(dispatcher) {

    override suspend fun execute(param: Int?): Flow<ViewResource<MovieViewParam>> {
        return flow {
            emit(ViewResource.Loading())
            param?.let { id ->
                repository.getMovieDetail(id).collect { dataResource ->
                    dataResource.suspendSubscribe(
                        doOnSuccess = {
                            emit(
                                ViewResource.Success(
                                    data = MovieMapper.toViewParam(dataResource.payload?.data)
                                )
                            )
                        },
                        doOnError = { error ->
                            emit(ViewResource.Error(exception = error.exception))
                        },
                    )
                }
            } ?: throw IllegalStateException("Param Required")
        }
    }

}