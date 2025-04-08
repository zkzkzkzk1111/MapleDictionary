package com.kmj.domain.util

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Failure<T>(val error: Throwable) : ApiResult<T>()
    class Loading<T>(val data: T? = null) : ApiResult<T>()
}

inline fun <T, R> ApiResult<T>.getResult(
    success: (ApiResult.Success<T>) -> R,
    failure: (ApiResult.Failure<T>) -> R,
    loading: (ApiResult.Loading<T>) -> R
): R = when (this) {
    is ApiResult.Success -> success(this)
    is ApiResult.Failure -> failure(this)
    is ApiResult.Loading -> loading(this)
}

inline fun <T> ApiResult<T>.onSuccess(
    action: (T) -> Unit
): ApiResult<T> {
    if (this is ApiResult.Success) action(data)
    return this
}

inline fun <T> ApiResult<T>.onFailure(
    action: (Throwable) -> Unit
): ApiResult<T> {
    if (this is ApiResult.Failure) action(error)
    return this
}