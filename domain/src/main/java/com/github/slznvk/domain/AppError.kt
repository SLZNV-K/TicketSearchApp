package com.github.slznvk.domain

sealed class AppError(var code: String) : RuntimeException() {
    companion object {
        fun from(e: Throwable): AppError = when (e) {
            is ApiError -> e
            is NetworkError -> NetworkError
            is IncorrectData -> IncorrectData
            else -> UnknownError
        }
    }
}

class ApiError(val status: Int, code: String) : AppError(code)
data object NetworkError : AppError("error_network")
data object UnknownError : AppError("error_unknown")

data object IncorrectData : AppError("error_incorrect_data")