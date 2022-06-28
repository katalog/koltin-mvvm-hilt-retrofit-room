package com.mkstudio.kotlin_mvvm_hilt_retrofit_room

import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.APIService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.BookDatabaseService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo.MainRepository
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.util.TestCaseGenerator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepositoryTest {
    private lateinit var repo: MainRepository

    private val api: APIService = mockk()
    private val db: BookDatabaseService = mockk()

    @Test
    fun `random books success test`() = runBlocking {
        val list = TestCaseGenerator().makeAPITestList(true)
        coEvery { api.getBookList(any()) } returns list

        repo = MainRepository(api, db)

        val result = repo.getRandomBooks()

        assert(result.isNotEmpty())
        assert(result.size == 3)
        assert(result[0].title == list.data!!.bookinfo[0].title)
        assert(result[1].authorname == list.data!!.bookinfo[1].authorinfo[0].name)
        assert(result[2].imgurl == list.data!!.bookinfo[2].formats.imgurl)
    }

    @Test
    fun `random books error test`() = runBlocking {
        val list = TestCaseGenerator().makeAPITestList(false)
        coEvery { api.getBookList(any()) } returns list

        repo = MainRepository(api, db)

        val result = repo.getRandomBooks()

        assert(result.isEmpty())
    }

    @Test
    fun `random books author empty test`() = runBlocking {
        val list = TestCaseGenerator().makeAPITestList(true, true)
        coEvery { api.getBookList(any()) } returns list

        repo = MainRepository(api, db)

        val result = repo.getRandomBooks()

        assert(result.isNotEmpty())
        assert(result.size == 2)
        assert(result[0].title == list.data!!.bookinfo[0].title)
        assert(result[1].authorname == list.data!!.bookinfo[2].authorinfo[0].name)
    }

    @Test
    fun `random books img empty test`() = runBlocking {
        val list = TestCaseGenerator().makeAPITestList(true, emptyimg = true)
        coEvery { api.getBookList(any()) } returns list

        repo = MainRepository(api, db)

        val result = repo.getRandomBooks()

        assert(result.isNotEmpty())
        assert(result.size == 2)
        assert(result[0].title == list.data!!.bookinfo[0].title)
        assert(result[1].authorname == list.data!!.bookinfo[2].authorinfo[0].name)
    }


    @Test
    fun `search books success test`() = runBlocking {
        val list = TestCaseGenerator().makeAPITestList(true)
        coEvery { api.searchBooks(any()) } returns list

        repo = MainRepository(api, db)

        val result = repo.searchBooks("pride")

        assert(result.isNotEmpty())
        assert(result.size == 3)
        assert(result[0].title == list.data!!.bookinfo[0].title)
        assert(result[1].authorname == list.data!!.bookinfo[1].authorinfo[0].name)
        assert(result[2].imgurl == list.data!!.bookinfo[2].formats.imgurl)
    }

    @Test
    fun `search books error test`() = runBlocking {
        val list = TestCaseGenerator().makeAPITestList(false)
        coEvery { api.searchBooks(any()) } returns list

        repo = MainRepository(api, db)

        val result = repo.searchBooks("pride")

        assert(result.isEmpty())
    }

    @Test
    fun `search books empty test`() = runBlocking {
        val list = TestCaseGenerator().makeAPITestEmptyList(true)
        coEvery { api.searchBooks(any()) } returns list

        repo = MainRepository(api, db)

        val result = repo.searchBooks("pride")

        assert(result.isEmpty())
    }

    @Test
    fun `search books empty fail test`() = runBlocking {
        val list = TestCaseGenerator().makeAPITestEmptyList(false)
        coEvery { api.searchBooks(any()) } returns list

        repo = MainRepository(api, db)

        val result = repo.searchBooks("pride")

        assert(result.isEmpty())
    }
}