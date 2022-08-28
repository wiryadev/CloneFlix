package com.wiryadev.core.wrapper

sealed class DataResource<T>(
    val payload: T? = null,
    val message: String? = null,
    val exception: Exception? = null,
) {

    class Success<T>(data: T) : DataResource<T>(data)

    class Error<T>(
        exception: Exception?,
        data: T? = null,
    ) : DataResource<T>(data, exception = exception)

}
