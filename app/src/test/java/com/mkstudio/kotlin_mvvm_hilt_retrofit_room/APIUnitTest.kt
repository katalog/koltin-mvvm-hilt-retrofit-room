package com.mkstudio.kotlin_mvvm_hilt_retrofit_room

import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.*
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test
import retrofit2.Response

class APIUnitTest {
    private val mockservice:RetrofitService = mockk()
    lateinit var api: APIService

    @Test
    fun `api calling test`() {
        val bookList = BookList(listOf<BookInfo>(BookInfo(-1, "",
            listOf<AuthorInfo>(AuthorInfo("")), FormatInfo(""))))

        coEvery { mockservice.getBookList(any()).execute() } returns Response.success<BookList>(bookList)

        api = APIService(mockservice)

        val resp = api.getBookList("123")
        assert(resp.size == 0)
    }

    @Test
    fun `if book author name empty`() {
        val bookList = BookList(listOf<BookInfo>(BookInfo(1234, "test1",
            listOf<AuthorInfo>(AuthorInfo("")), FormatInfo("123123"))))

        coEvery { mockservice.getBookList(any()).execute() } returns Response.success<BookList>(bookList)

        api = APIService(mockservice)

        val resp = api.getBookList("123")
        assert(resp.size == 1)
        assert(resp[0].id != -1)
        assert(resp[0].title.isNotEmpty())
        assert(resp[0].authorname == "Unknown")
        assert(resp[0].imgurl.isNotEmpty())
    }

    @Test
    fun `if book img url empty`() {
        val bookList = BookList(listOf<BookInfo>(BookInfo(1234, "test1",
            listOf<AuthorInfo>(AuthorInfo("nona")), FormatInfo(""))))

        coEvery { mockservice.getBookList(any()).execute() } returns Response.success<BookList>(bookList)

        api = APIService(mockservice)

        val resp = api.getBookList("123")
        assert(resp.size == 0)
    }
}