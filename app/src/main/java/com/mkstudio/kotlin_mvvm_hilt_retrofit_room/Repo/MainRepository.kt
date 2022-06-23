package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo

import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.APIService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.Book
import javax.inject.Inject
import kotlin.random.Random

class MainRepository @Inject constructor(private val api:APIService){
    fun getRandomBooks() : List<Book> {
        val nRandom = Random.nextInt(2000)+1
        val resp = api.getBookList(nRandom.toString())
        return resp
    }
}