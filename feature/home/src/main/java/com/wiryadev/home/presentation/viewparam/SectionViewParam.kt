package com.wiryadev.home.presentation.viewparam

import com.wiryadev.shared.data.model.viewparam.MovieViewParam

data class SectionViewParam(
    val id: Int,
    val name: String,
    val contents: List<MovieViewParam>,
)
