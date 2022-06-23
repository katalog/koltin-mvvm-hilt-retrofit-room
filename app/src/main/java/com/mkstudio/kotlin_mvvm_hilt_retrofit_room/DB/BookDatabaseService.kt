package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB

import javax.inject.Inject

class BookDatabaseService @Inject constructor(private val bookDao: BookDao) {
    suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

    suspend fun getBook(id:Int) : Book? {
        return bookDao.getBook(id)
    }

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun deleteBook(id:Int) {
        bookDao.deleteBook(id)
    }
}