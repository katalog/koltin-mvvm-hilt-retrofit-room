package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API

import com.google.gson.annotations.SerializedName

data class BookList(
    @SerializedName("results")
    val bookinfo: List<BookInfo>
)

data class BookInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title:String,
    @SerializedName("authors")
    val authorinfo: List<AuthorInfo>,
    @SerializedName("formats")
    val formats: FormatInfo
)

data class AuthorInfo(
    @SerializedName("name")
    val name: String
)

data class FormatInfo(
    @SerializedName("image/jpeg")
    val imgurl: String
)

data class CombinedInfo(
    val id: Int,
    val title: String,
    var authorname: String,
    val imgurl: String
)