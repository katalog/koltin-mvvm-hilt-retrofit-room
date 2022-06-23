package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/books/")
    fun getBookList(@Query("page") pagenumber:String, @Query("languages")lang:String="en"): Call<JSONBookList>
}