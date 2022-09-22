package com.wiryadev.home.presentation.adapter.home

import com.wiryadev.shared.data.model.viewparam.MovieViewParam

interface HomeAdapterClickListener {
    fun onMyListClicked(movieViewParam: MovieViewParam)
    fun onPlayMovieClicked(movieViewParam: MovieViewParam)
    fun onMovieClicked(movieViewParam: MovieViewParam)
}