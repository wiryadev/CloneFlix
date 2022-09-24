package com.wiryadev.home.presentation.adapter.home

import com.wiryadev.shared.data.model.viewparam.MovieViewParam

interface HomeAdapterClickListener {
    fun onMyListClicked(movieViewParam: MovieViewParam)
    fun onPlayMovieClicked(movieViewParam: MovieViewParam)
    fun onMovieClicked(movieViewParam: MovieViewParam)
}

fun createHomeAdapterClickListener(
     onMyListClicked: ((MovieViewParam) -> Unit)? = null,
     onPlayMovieClicked: ((MovieViewParam) -> Unit)? = null,
     onMovieClicked: ((MovieViewParam)-> Unit)? = null,
) : HomeAdapterClickListener {
    return object : HomeAdapterClickListener {
        override fun onMyListClicked(movieViewParam: MovieViewParam) {
            onMyListClicked?.invoke(movieViewParam)
        }

        override fun onPlayMovieClicked(movieViewParam: MovieViewParam) {
            onPlayMovieClicked?.invoke(movieViewParam)
        }

        override fun onMovieClicked(movieViewParam: MovieViewParam) {
            onMovieClicked?.invoke(movieViewParam)
        }
    }
}