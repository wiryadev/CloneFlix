package com.wiryadev.home.mapper

import com.wiryadev.home.data.network.model.HomeFeedResponse
import com.wiryadev.home.presentation.viewparam.HomeFeedViewParam
import com.wiryadev.shared.data.model.mapper.MovieMapper
import com.wiryadev.shared.utils.mapper.ListMapper
import com.wiryadev.shared.utils.mapper.ViewParamMapper

object HomeFeedMapper : ViewParamMapper<HomeFeedResponse, HomeFeedViewParam> {

    override fun toViewParam(dataObject: HomeFeedResponse?): HomeFeedViewParam {
        return HomeFeedViewParam(
            header = MovieMapper.toViewParam(dataObject?.header),
            sections = ListMapper(SectionMapper).toViewParams(dataObject?.sections)
        )
    }

}