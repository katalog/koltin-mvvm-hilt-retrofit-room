package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API

import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import javax.inject.Inject

class APIService @Inject constructor(private val service: RetrofitService) {
    fun getBookList(pageNumber: String): List<Book> {
        var resp = service.getBookList(pageNumber).execute()
        val booklist = resp.body()
        var newlist = listOf<Book>()

        booklist?.let {
            newlist = makeBookStruct(it)
        }

        return newlist
    }

    fun searchBooks(searchquery: String): List<Book> {
        var resp = service.searchBooks(searchquery).execute()
        val booklist = resp.body()
        var newlist = listOf<Book>()

        booklist?.let {
            newlist = makeBookStruct(it)
        }

        return newlist
    }

    private fun makeBookStruct(jsonBookList: JSONBookList): List<Book> {
        val newlist = mutableListOf<Book>()

        jsonBookList.bookinfo.forEach { bookinfo ->
            var newInfo = Book(bookinfo.id, bookinfo.title, "", "")

            if (bookinfo.authorinfo.isNotEmpty()) {
                newInfo.authorname = bookinfo.authorinfo.first().name
            }
            bookinfo.formats.imgurl?.let { newInfo.imgurl = it }

            if (newInfo.imgurl.isNotEmpty() and newInfo.authorname.isNotEmpty()) {
                newlist.add(newInfo)
            }
        }

        return newlist
    }
}