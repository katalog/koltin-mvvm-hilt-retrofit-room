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
        val JSONBookList = JSONBookList(listOf<JSONBookInfo>(JSONBookInfo(-1, "aa",
            listOf<JSONAuthorInfo>(JSONAuthorInfo("aa")), JSONFormatInfo("http://www.1.1.1"))))

        coEvery { mockservice.getBookList(any()).execute() } returns Response.success<JSONBookList>(JSONBookList)

        api = APIService(mockservice)

        val resp = api.getBookList("123")
        assert(resp.size == 1)
        assert(resp[0].id == -1)
        assert(resp[0].title == "aa")
        assert(resp[0].authorname == "aa")
        assert(resp[0].imgurl == "http://www.1.1.1")
    }

    @Test
    fun `author name empty`() {
        val JSONBookList = JSONBookList(listOf<JSONBookInfo>(JSONBookInfo(1234, "test1",
            listOf<JSONAuthorInfo>(JSONAuthorInfo("")), JSONFormatInfo("123123"))))

        coEvery { mockservice.getBookList(any()).execute() } returns Response.success<JSONBookList>(JSONBookList)

        api = APIService(mockservice)

        val resp = api.getBookList("123")
        assert(resp.size == 0)
    }

    @Test
    fun `img url empty`() {
        val JSONBookList = JSONBookList(listOf<JSONBookInfo>(JSONBookInfo(1234, "test1",
            listOf<JSONAuthorInfo>(JSONAuthorInfo("nona")), JSONFormatInfo(""))))

        coEvery { mockservice.getBookList(any()).execute() } returns Response.success<JSONBookList>(JSONBookList)

        api = APIService(mockservice)

        val resp = api.getBookList("123")
        assert(resp.size == 0)
    }
}