package com.barisguneri.earthquakeapp.core.common

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val errorType: ErrorType) : Resource<T>()
    class Loading<T> : Resource<T>()

}