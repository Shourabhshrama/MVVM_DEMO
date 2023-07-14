package com.example.softgridapp.utils


sealed class NetworkResult<T> {
    class Loading<T> : NetworkResult<T>()

    data class Success<T>(val data: T) : NetworkResult<T>()

    data class Error<T>(val message: String) : NetworkResult<T>()

    data class NetworkError<T>(val netWorkMessage: String) : NetworkResult<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
        fun <T> networkError(message: String) = NetworkError<T>(message)
    }
}