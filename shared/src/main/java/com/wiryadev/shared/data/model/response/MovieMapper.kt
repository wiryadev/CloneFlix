package com.wiryadev.shared.data.model.response

import com.wiryadev.shared.data.model.viewparam.MovieViewParam
import com.wiryadev.shared.utils.mapper.ViewParamMapper

object MovieMapper : ViewParamMapper<MovieResponse, MovieViewParam> {
    override fun toViewParam(dataObject: MovieResponse?): MovieViewParam {
        return MovieViewParam(
            cast = dataObject?.cast.orEmpty(),
            category = dataObject?.category.orEmpty(),
            director = dataObject?.director.orEmpty(),
            filmRate = dataObject?.filmRate.orEmpty(),
            id = dataObject?.id ?: -1,
            isUserWatchlist = dataObject?.isUserWatchlist ?: false,
            overview = dataObject?.overview.orEmpty(),
            posterUrl = dataObject?.posterUrl.orEmpty(),
            releaseDate = dataObject?.releaseDate.orEmpty(),
            title = dataObject?.title.orEmpty(),
            trailerUrl = dataObject?.trailerUrl.orEmpty(),
            videoUrl = dataObject?.videoUrl.orEmpty(),
            runtime = dataObject?.runtime ?: -1,
        )
    }
}