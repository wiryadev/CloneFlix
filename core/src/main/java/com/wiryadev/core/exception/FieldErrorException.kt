package com.wiryadev.core.exception

class FieldErrorException(
    val errorFields: List<Pair<Int, Int>>
) : Exception()