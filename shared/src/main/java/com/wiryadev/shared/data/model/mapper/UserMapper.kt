package com.wiryadev.shared.data.model.mapper

import com.wiryadev.shared.data.model.response.UserResponse
import com.wiryadev.shared.data.model.viewparam.UserViewParam
import com.wiryadev.shared.utils.mapper.ViewParamMapper

object UserMapper : ViewParamMapper<UserResponse, UserViewParam> {

    override fun toViewParam(dataObject: UserResponse?): UserViewParam {
        return UserViewParam(
            id = dataObject?.id ?: -1,
            username = dataObject?.username.orEmpty(),
            email = dataObject?.email.orEmpty(),
            birthdate = dataObject?.birthdate.orEmpty(),
            gender = dataObject?.gender ?: -1,
        )
    }

}