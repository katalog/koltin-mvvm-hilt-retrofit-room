package com.mkstudio.kotlin_mvvm_hilt_retrofit_room

import android.widget.SearchView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.APIService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.BookDatabaseService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo.MainRepository
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel.MainViewModel
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel.SearchBooksViewModel
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.util.InstantExecutorExtension
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.util.MainCoroutineRule
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.util.TestCaseGenerator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class ViewModelSearchTest {
    private lateinit var viewmodel: SearchBooksViewModel
    private lateinit var repo: MainRepository

    private val api: APIService = mockk()
    private val db: BookDatabaseService = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `search success test`() {
        val testList = TestCaseGenerator().makeAPITestList(true)
        coEvery { api.searchBooks(any()) } returns testList

        repo = MainRepository(api, db)
        viewmodel = SearchBooksViewModel(repo)

        viewmodel.searchBook("yiii")
        viewmodel.SearchBookList.observeForever {  }
        val list = viewmodel.SearchBookList.value

        assert(list != null)
        assert(list!!.isNotEmpty())
        assert(list.size == 3)
        assert(list[0].authorname == testList.data!!.bookinfo[0].authorinfo[0].name)
        assert(list[1].title == testList.data!!.bookinfo[1].title)
        assert(list[2].imgurl == testList.data!!.bookinfo[2].formats.imgurl)
    }

    @Test
    fun `search fail test`() {
        val testList = TestCaseGenerator().makeAPITestList(false)
        coEvery { api.searchBooks(any()) } returns testList

        repo = MainRepository(api, db)
        viewmodel = SearchBooksViewModel(repo)

        viewmodel.searchBook("yiii")
        viewmodel.SearchFailed.observeForever {  }
        val ret = viewmodel.SearchFailed.value

        assert(ret == true)
    }

    @Test
    fun `search success empty test`() {
        val testList = TestCaseGenerator().makeAPITestEmptyList(true)
        coEvery { api.searchBooks(any()) } returns testList

        repo = MainRepository(api, db)
        viewmodel = SearchBooksViewModel(repo)

        viewmodel.searchBook("yiii")
        viewmodel.SearchBookList.observeForever {  }
        val list = viewmodel.SearchBookList.value

        assert(list == null)
    }
}