package com.wiryadev.home.data.network.model

import com.google.gson.annotations.SerializedName
import com.wiryadev.shared.data.model.response.MovieResponse

data class HomeFeedResponse(
    @SerializedName("header")
    val header: MovieResponse?,

    @SerializedName("sections")
    val sections: List<SectionResponse>?,
)
