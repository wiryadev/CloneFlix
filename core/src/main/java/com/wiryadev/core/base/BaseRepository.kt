package com.wiryadev.core.base

import com.wiryadev.core.exception.ApiErrorException
import com.wiryadev.core.exception.NoInternetConnectionException
import com.wiryadev.core.exception.UnexpectedErrorException
import com.wiryadev.core.wrapper.DataResource
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRepository {

    abstract fun <T> getErrorMessageFromApi(response: T): String

    suspend fun <T> safeNetworkCall(apiCall: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> DataResource.Error(NoInternetConnectionException())
                is HttpException -> {
                    DataResource.Error(
                        ApiErrorException(
                            getErrorMessageFromApi(
                                throwable.response()?.errorBody()
                            ), throwable.code()
                        )
                    )
                }
                else -> {
                    DataResource.Error(UnexpectedErrorException())
                }
            }
        }
    }

    suspend fun <T> proceed(coroutine: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            DataResource.Error(exception)
        }
    }

}