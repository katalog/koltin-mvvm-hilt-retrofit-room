package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.util

import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.*
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book

class TestCaseGenerator {
    private var jsonBookList = JSONBookList(
        listOf(
            JSONBookInfo(
                1112221, "title 1",
                listOf(JSONAuthorInfo("test auth 1")), JSONFormatInfo("test-img-1")
            ),
            JSONBookInfo(
                1112222, "title 2",
                listOf(JSONAuthorInfo("test auth 2")), JSONFormatInfo("test-img-2")
            ),
            JSONBookInfo(
                1112223, "title 3",
                listOf(JSONAuthorInfo("test auth 3")), JSONFormatInfo("test-img-3")
            ),
        )
    )

    private var booklist = listOf(
        Book(111221, "title 1", "author 1", "img 1"),
        Book(111222, "title 2", "author 2", "img 2"),
        Book(111223, "title 3", "author 3", "img 3"),
        Book(111224, "title 4", "author 4", "img 4"),
    )

    fun makeAPITestList(success: Boolean, emptyauthor: Boolean = false, emptyimg: Boolean = false):
            APIResponse<JSONBookList> {
        if (emptyauthor) {
            jsonBookList.bookinfo[1].authorinfo[0].name = ""
        }

        if (emptyimg) {
            jsonBookList.bookinfo[1].formats.imgurl = ""
        }

        return if (success) {
            APIResponse.Success(jsonBookList)
        } else {
            APIResponse.DataError(-1)
        }
    }

    fun makeAPITestEmptyList(success: Boolean): APIResponse<JSONBookList> {
        var emptylist = JSONBookList(
            listOf(
                JSONBookInfo(
                    0, "",
                    listOf(JSONAuthorInfo("")), JSONFormatInfo("")
                ),
            )
        )

        return if (success) {
            APIResponse.Success(emptylist)
        } else {
            APIResponse.DataError(-1)
        }
    }

    fun makeDBTestList() : List<Book> {
        return booklist
    }
}
