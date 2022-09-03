package com.wiryadev.shared.data.model.viewparam

data class MovieViewParam(
    val id: Int,
    val cast: List<String>,
    val category: List<String>,
    val director: String,
    val filmRate: String,
    val overview: String,
    val posterUrl: String,
    val releaseDate: String,
    val runtime: Int,
    val title: String,
    val trailerUrl: String,
    val videoUrl: String,
    var isUserWatchlist: Boolean,
)