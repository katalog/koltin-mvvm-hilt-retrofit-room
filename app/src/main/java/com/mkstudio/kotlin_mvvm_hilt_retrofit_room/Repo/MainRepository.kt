package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo

import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.APIService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.JSONBookList
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.BookDatabaseService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.Util.API_MAX_PAGE_NUMBER
import javax.inject.Inject
import kotlin.random.Random

class MainRepository @Inject constructor(private val api:APIService, private val db:BookDatabaseService){
    suspend fun getRandomBooks(): List<Book> {
        val nRandom = Random.nextInt(API_MAX_PAGE_NUMBER) + 1
        var books = listOf<Book>()
        val response = api.getBookList(nRandom.toString())
        if ( response.isSuccess() ) {
            books = makeBookStruct(response.data as JSONBookList)
        }
        return books
    }

    suspend fun searchBooks(searchquery: String): List<Book> {
        var books = listOf<Book>()
        val response = api.searchBooks(searchquery)
        if ( response.isSuccess() ) {
            books = makeBookStruct(response.data as JSONBookList)
        }
        return books
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

    private fun makeBookStruct(jsonBookList: JSONBookList): List<Book> {
        val newlist = mutableListOf<Book>()

        jsonBookList.bookinfo.forEach { bookinfo ->
            var newInfo = Book(bookinfo.id, bookinfo.title, "", "")

            if (bookinfo.authorinfo.isNotEmpty()) {
                newInfo.authorname = bookinfo.authorinfo.first().name
            }

            if ( bookinfo.formats.imgurl.isNotEmpty() ) {
                newInfo.imgurl = bookinfo.formats.imgurl
            }

            if (newInfo.imgurl.isNotEmpty() and newInfo.authorname.isNotEmpty()) {
                newlist.add(newInfo)
            }
        }

        return newlist
    }
}