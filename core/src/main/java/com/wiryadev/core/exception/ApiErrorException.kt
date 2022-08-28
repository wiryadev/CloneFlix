package com.wiryadev.core.exception

class ApiErrorException(
    override val message: String? = null,
    val httpCode: Int? = null,
) : Exception()