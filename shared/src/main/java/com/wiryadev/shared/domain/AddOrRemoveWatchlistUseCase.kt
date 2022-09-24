package com.wiryadev.shared.domain

import com.wiryadev.core.base.BaseUseCase
import com.wiryadev.core.wrapper.DataResource
import com.wiryadev.core.wrapper.ViewResource
import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.data.repository.SharedApiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class AddOrRemoveWatchlistUseCase(
    val repository: SharedApiRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<AddOrRemoveWatchlistUseCase.Param, MovieViewParam>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<MovieViewParam>> {
        param?.let {
            val movie = param.movie
            val movieId = movie.id
            val action = if (param.movie.isUserWatchlist) {
                repository.removeWatchlist(movieId.toString())
            } else {
                repository.addWatchlist(movieId.toString())
            }

            return action.map { result ->
                when (result) {
                    is DataResource.Success -> ViewResource.Success(movie.apply {
                        isUserWatchlist = !isUserWatchlist
                    })

                    is DataResource.Error -> ViewResource.Error(result.exception)
                }
            }.onStart { emit(ViewResource.Loading()) }

        } ?: throw IllegalStateException("Param Required")
    }

    data class Param(val movie: MovieViewParam)

}