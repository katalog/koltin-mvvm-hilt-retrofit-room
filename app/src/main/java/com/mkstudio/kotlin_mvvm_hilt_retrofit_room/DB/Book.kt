package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    var authorname: String,
    var imgurl: String,
    val favorite:Boolean = false,
)