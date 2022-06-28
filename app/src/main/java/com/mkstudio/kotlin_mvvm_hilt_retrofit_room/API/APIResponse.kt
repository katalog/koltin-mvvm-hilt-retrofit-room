package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API

sealed class APIResponse<T>(
    val data: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : APIResponse<T>(data)
    class DataError<T>(errorCode: Int) : APIResponse<T>(null, errorCode)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorCode]"
        }
    }

    fun isSuccess():Boolean {
        return when (this) {
            is Success<*> -> true
            is DataError -> false
        }
    }
}

const val NO_INTERNET_CONNECTION = -1
const val NETWORK_ERROR = -2
const val DEFAULT_ERROR = -3
const val NETWORK_RANDOM_ERROR = -11
const val NETWORK_SEARCH_ERROR = -12