package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Query("Select * from Book")
    suspend fun getAllBooks(): List<Book>

    @Query("Select * from Book where id = :bookid")
    suspend fun getBook(bookid:Int) : Book?

    @Query("Delete from Book where id = :bookid")
    suspend fun deleteBook(bookid:Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)
}