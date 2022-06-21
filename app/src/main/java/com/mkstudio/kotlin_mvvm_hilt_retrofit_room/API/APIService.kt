package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API

import javax.inject.Inject

class APIService @Inject constructor(private val service: RetrofitService) {
    fun getBookList(pageNumber:String): List<CombinedInfo> {
        var resp = service.getBookList(pageNumber).execute()
        val booklist = resp.body()
        val newlist = mutableListOf<CombinedInfo>()

        booklist?.let {
            it.bookinfo.forEach { it2 ->
                if ( it2.formats.imgurl.isEmpty() ) return@forEach

                var newInfo = CombinedInfo(it2.id, it2.title, "Unknown", it2.formats.imgurl)
                if ( it2.authorinfo.isNotEmpty() ) {
                    if ( it2.authorinfo[0].name.isNotEmpty() ) {
                        newInfo.authorname = it2.authorinfo[0].name
                    }
                }
                newlist.add(newInfo)
            }
        }

        return newlist
    }
}