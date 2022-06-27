package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/books/")
    suspend fun getBookList(@Query("page") pagenumber:String, @Query("languages")lang:String="en"): JSONBookList

    @GET("/books/")
    suspend fun searchBooks(@Query("search") searchquery:String, @Query("languages")lang:String="en"): JSONBookList
}