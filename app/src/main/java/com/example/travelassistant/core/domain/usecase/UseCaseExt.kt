package com.example.travelassistant.core.domain.usecase

import com.example.travelassistant.core.domain.State
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

inline fun <T> safeCall(action: () -> T): State<T> {
    return try {
        State.Success(action.invoke())
    } catch (throwable: Throwable) {
        State.Error(throwable.isNetworkException())
    }
}

inline fun safeVoidCall(action: () -> Void): State<Unit> {
    return try {
        action.invoke()
        State.Success(Unit)
    } catch (throwable: Throwable) {
        State.Error(throwable.isNetworkException())
    }
}

fun Throwable?.isNetworkException(): Boolean {
    return when (this) {
        is ConnectException,
        is SocketException,
        is SocketTimeoutException,
        is UnknownHostException,
        is ProtocolException -> true
        else -> false
    }
}
