package com.towich.vktest.util

sealed class ApiResult<out R> {
    data object Loading: ApiResult<Nothing>()
    data class Success<out T>(val data: T): ApiResult<T>()
    data class Error(val error: String): ApiResult<Nothing>()
}
