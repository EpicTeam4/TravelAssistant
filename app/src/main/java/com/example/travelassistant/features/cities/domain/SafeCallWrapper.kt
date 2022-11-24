package com.example.travelassistant.features.cities.domain

suspend fun <T> safeCall(invokeFunction: suspend () -> T): Result<T> {

    return try {
        Result.success(invokeFunction.invoke())
    } catch (exception: Exception) {
        Result.failure(exception)
    }

}