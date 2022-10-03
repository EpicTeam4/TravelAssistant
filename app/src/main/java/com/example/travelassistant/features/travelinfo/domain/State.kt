package com.example.travelassistant.features.travelinfo.domain

sealed class State<out R> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val isNetworkError: Boolean) : State<Nothing>()
}

val <T> State<T>.data: T?
    get() = (this as? State.Success)?.data