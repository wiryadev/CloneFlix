package com.wiryadev.home.mapper

import com.wiryadev.home.data.network.model.SectionResponse
import com.wiryadev.home.presentation.viewparam.SectionViewParam
import com.wiryadev.shared.data.model.mapper.MovieMapper
import com.wiryadev.shared.utils.mapper.ListMapper
import com.wiryadev.shared.utils.mapper.ViewParamMapper

object SectionMapper : ViewParamMapper<SectionResponse, SectionViewParam> {

    override fun toViewParam(dataObject: SectionResponse?): SectionViewParam {
        return SectionViewParam(
            id =  dataObject?.sectionId ?: -1,
            name = dataObject?.sectionName.orEmpty(),
            contents = ListMapper(MovieMapper).toViewParams(dataObject?.contents)
        )
    }

}