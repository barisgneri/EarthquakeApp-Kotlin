package com.barisguneri.earthquakeapp.core.data

sealed class ErrorType {
    object NoInternetConnection : ErrorType()

    //{"status":false,"httpStatus":429,"desc":"Too Many Request in 1 minute! Requests limited in 1 minute maximum 40 times"}
    object TooManyRequests : ErrorType()

    data class HttpError(val code: Int, val message: String?) : ErrorType()

    data class ApiError(val apiCode: Int?, val message: String?) : ErrorType()

    object SerializationError : ErrorType()

    data class Unknown(val apiCode: Int?, val message: String?) : ErrorType()
}

data class PagingException(val errorType: ErrorType) : Exception()