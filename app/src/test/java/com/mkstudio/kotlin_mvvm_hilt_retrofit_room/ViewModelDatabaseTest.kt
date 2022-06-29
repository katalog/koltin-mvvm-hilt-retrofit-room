package com.mkstudio.kotlin_mvvm_hilt_retrofit_room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.APIService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.BookDatabaseService
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo.MainRepository
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel.MainViewModel
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
class ViewModelDatabaseTest {
    private lateinit var viewmodel: MainViewModel
    private lateinit var repo: MainRepository

    private val api: APIService = mockk()
    private val db: BookDatabaseService = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `insert test`() {
        val dblist = mutableListOf<Book>()
        val samplebook = TestCaseGenerator().makeDBTestList()[0]

        coEvery { db.insertBook(any()) } returns Unit

        repo = MainRepository(api, db)
        viewmodel = MainViewModel(repo)

        viewmodel.addFavorite(samplebook)
        viewmodel.FavoriteBookList.observeForever {  }
        val list = viewmodel.FavoriteBookList.value

        assert(list != null)
        assert(list!!.isNotEmpty())
        assert(list.size == 1)
    }

    @Test
    fun `multiple insert test`() {
        val dblist = mutableListOf<Book>()
        val samplelist = TestCaseGenerator().makeDBTestList()

        repo = MainRepository(api, db)
        viewmodel = MainViewModel(repo)

        coEvery { db.insertBook(any()) } returns Unit

        (0..2).forEach{
            viewmodel.addFavorite(samplelist[it])
            viewmodel.FavoriteBookList.observeForever {  }
        }
        val list = viewmodel.FavoriteBookList.value

        assert(list != null)
        assert(list!!.isNotEmpty())
        assert(list.size == 3)
        assert(list[0].title == samplelist[0].title)
        assert(list[1].authorname == samplelist[1].authorname)
        assert(list[2].imgurl == samplelist[2].imgurl)
    }

    @Test
    fun `multiple insert delete test`() {
        val dblist = mutableListOf<Book>()
        val samplelist = TestCaseGenerator().makeDBTestList()

        repo = MainRepository(api, db)
        viewmodel = MainViewModel(repo)

        coEvery { db.insertBook(any()) } returns Unit
        coEvery { db.deleteBook(any()) } returns Unit

        (0..2).forEach{
            viewmodel.addFavorite(samplelist[it])
            viewmodel.FavoriteBookList.observeForever {  }
        }

        viewmodel.deleteFavorite(samplelist[1])
        viewmodel.FavoriteBookList.observeForever {  }

        val list = viewmodel.FavoriteBookList.value

        assert(list != null)
        assert(list!!.isNotEmpty())
        assert(list.size == 2)
        assert(list[0].title == samplelist[0].title)
        assert(list[1].imgurl == samplelist[2].imgurl)
    }

    @Test
    fun `initial getall test`() {
        val dblist = mutableListOf<Book>()
        val samplelist = TestCaseGenerator().makeDBTestList()

        coEvery { db.insertBook(any()) } returns Unit
        coEvery { db.getAllBooks() } returns dblist as List<Book>

        repo = MainRepository(api, db)
        viewmodel = MainViewModel(repo)

        viewmodel.getFavorite()
        viewmodel.FavoriteBookList.observeForever {  }
        val list = viewmodel.FavoriteBookList.value!!

        assert(list.isEmpty())
    }

    @Test
    fun `multiple insert delete verify test`() {
        val dblist = mutableListOf<Book>()
        val samplelist = TestCaseGenerator().makeDBTestList()

        repo = MainRepository(api, db)
        viewmodel = MainViewModel(repo)

        coEvery { db.insertBook(any()) } returns Unit
        coEvery { db.deleteBook(any()) } returns Unit

        (0..2).forEach{
            viewmodel.addFavorite(samplelist[it])
            viewmodel.FavoriteBookList.observeForever {  }
        }

        viewmodel.deleteFavorite(samplelist[1])
        viewmodel.FavoriteBookList.observeForever {  }

        val list = viewmodel.getFavoriteCache()

        assert(list != null)
        assert(list!!.isNotEmpty())
        assert(list.size == 2)
        assert(list[0].title == samplelist[0].title)
        assert(list[1].imgurl == samplelist[2].imgurl)
    }

    @Test
    fun `multiple delete from empty db test`() {
        val dblist = mutableListOf<Book>()
        val samplelist = TestCaseGenerator().makeDBTestList()

        repo = MainRepository(api, db)
        viewmodel = MainViewModel(repo)

        coEvery { db.deleteBook(any()) } returns Unit

        (0..2).forEach{
            viewmodel.deleteFavorite(samplelist[1])
            viewmodel.FavoriteBookList.observeForever {  }
        }

        val list = viewmodel.FavoriteBookList.value

        assert(list == null)
    }
}