package com.wiryadev.home.presentation.viewparam.homeitem

import com.wiryadev.home.presentation.viewparam.SectionViewParam
import com.wiryadev.shared.data.model.viewparam.MovieViewParam

sealed class HomeUiItem {
    class HeaderSectionItem(val movieViewParam: MovieViewParam) : HomeUiItem()
    class MovieSectionItem(val sectionViewParam: SectionViewParam) : HomeUiItem()
}
