package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo

import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.APIService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.BookDatabaseService
import javax.inject.Inject
import kotlin.random.Random

class MainRepository @Inject constructor(private val api:APIService, private val db:BookDatabaseService){
    suspend fun getRandomBooks() : List<Book> {
        val nRandom = Random.nextInt(2000)+1
        val resp = api.getBookList(nRandom.toString())
        return resp
    }

    suspend fun searchBooks(searchquery:String) : List<Book> {
        val resp = api.searchBooks(searchquery)
        return resp
    }

    suspend fun getAllBooks() : List<Book> {
        return db.getAllBooks()
    }

    suspend fun getBook(id:Int) : Book? {
        return db.getBook(id)
    }

    suspend fun insertBook(book:Book) {
        db.insertBook(book)
    }

    suspend fun deleteBook(id:Int) {
        db.deleteBook(id)
    }
}