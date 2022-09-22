package com.wiryadev.home.presentation.viewparam

import com.wiryadev.shared.data.model.viewparam.MovieViewParam

data class HomeFeedViewParam(
    val header: MovieViewParam,
    val sections: List<SectionViewParam>,
)