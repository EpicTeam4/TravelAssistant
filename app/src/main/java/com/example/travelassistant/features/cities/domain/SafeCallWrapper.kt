package com.example.travelassistant.features.cities.domain

import retrofit2.HttpException

suspend fun <T> safeCall(invokeFunction: suspend () -> T): Result<T> {

    return try {
        Result.success(invokeFunction.invoke())
    } catch (exception: HttpException) {
        Result.failure(exception)
    }

}