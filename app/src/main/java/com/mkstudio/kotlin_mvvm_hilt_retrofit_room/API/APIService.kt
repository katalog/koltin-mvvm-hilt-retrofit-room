package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API

import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import javax.inject.Inject

class APIService @Inject constructor(private val service: RetrofitService) {
    fun getBookList(pageNumber: String): List<Book> {
        var resp = service.getBookList(pageNumber).execute()
        val booklist = resp.body()
        val newlist = mutableListOf<Book>()

        booklist?.let {
            it.bookinfo.forEach { it2 ->
                var newInfo = Book(it2.id, it2.title, "", "")

                if (it2.authorinfo.isNotEmpty()) {
                    newInfo.authorname = it2.authorinfo.first().name
                }
                it2.formats.imgurl.let { newInfo.imgurl = it }

                if (newInfo.imgurl.isNotEmpty() and newInfo.authorname.isNotEmpty()) {
                    newlist.add(newInfo)
                }
            }
        }

        return newlist
    }
}