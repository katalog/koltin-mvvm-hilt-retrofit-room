package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class],
    version = 1,
    exportSchema = false
)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDAO(): BookDao
}