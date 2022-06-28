package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API

import android.util.Log
import javax.inject.Inject

class APIService @Inject constructor(private val service: RetrofitService) {
    suspend fun getBookList(pageNumber: String): APIResponse<JSONBookList> {
        return try {
            val jsonBookList = service.getBookList(pageNumber)
            if (jsonBookList.bookinfo.isNotEmpty()) {
                APIResponse.Success(data = jsonBookList)
            } else {
                APIResponse.DataError(errorCode = NETWORK_RANDOM_ERROR)
            }
        } catch (e: Exception) {
            Log.d("MYTAG", "api random error = ${e.stackTraceToString()}")
            APIResponse.DataError(errorCode = NETWORK_ERROR)
        }
    }

    suspend fun searchBooks(searchquery: String): APIResponse<JSONBookList> {
        return try {
            val jsonBookList = service.searchBooks(searchquery)
            if (jsonBookList.bookinfo.isNotEmpty()) {
                APIResponse.Success(data = jsonBookList)
            } else {
                APIResponse.DataError(errorCode = NETWORK_SEARCH_ERROR)
            }
        } catch (e: Exception) {
            Log.d("MYTAG", "api search error = ${e.stackTraceToString()}")
            APIResponse.DataError(errorCode = NETWORK_ERROR)
        }
    }
}