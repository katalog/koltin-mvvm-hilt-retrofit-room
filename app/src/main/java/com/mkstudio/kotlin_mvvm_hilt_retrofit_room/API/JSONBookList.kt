package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API

import com.google.gson.annotations.SerializedName

data class JSONBookList(
    @SerializedName("results")
    val bookinfo: List<JSONBookInfo>
)

data class JSONBookInfo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title:String,
    @SerializedName("authors")
    val authorinfo: List<JSONAuthorInfo>,
    @SerializedName("formats")
    val formats: JSONFormatInfo
)

data class JSONAuthorInfo(
    @SerializedName("name")
    var name: String
)

data class JSONFormatInfo(
    @SerializedName("image/jpeg")
    var imgurl: String
)
