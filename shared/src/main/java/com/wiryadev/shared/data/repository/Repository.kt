package com.wiryadev.shared.data.repository

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.wiryadev.core.base.BaseRepository
import com.wiryadev.shared.data.model.response.BaseResponse
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent.inject

open class Repository : BaseRepository() {

    private val gson: Gson by inject(Gson::class.java)

    override fun <T> getErrorMessageFromApi(response: T): String {
        val responseBody = response as ResponseBody
        return try {
            val body = gson.fromJson(
                responseBody.toString(),
                BaseResponse::class.java,
            )
            body.message ?: "Error Api"
        } catch (e: JsonParseException) {
            "Error Api"
        }
    }

}