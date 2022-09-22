package com.wiryadev.home.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.home.data.repository.HomeRepository
import com.wiryadev.shared.data.model.mapper.MovieMapper
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.utils.ext.suspendSubscribe
import com.wiryadev.shared.utils.mapper.ListMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserWatchlistUseCase(
    private val repository: HomeRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Nothing, List<MovieViewParam>>(dispatcher) {

    override suspend fun execute(param: Nothing?): Flow<ViewResource<List<MovieViewParam>>> {
        return flow {
            emit(ViewResource.Loading())

            repository.fetchWatchlist().collect { dataResource ->
                dataResource.suspendSubscribe(
                    doOnSuccess = { response ->
                        val movies = response.payload?.data
                        if (movies.isNullOrEmpty()) {
                            emit(ViewResource.Empty())
                        } else {
                            emit(ViewResource.Success(ListMapper(MovieMapper).toViewParams(movies)))
                        }
                    },
                    doOnError = { error ->
                        emit(ViewResource.Error(error.exception))
                    }
                )
            }
        }
    }

}