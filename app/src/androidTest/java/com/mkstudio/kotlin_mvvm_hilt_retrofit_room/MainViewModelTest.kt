package com.mkstudio.kotlin_mvvm_hilt_retrofit_room

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo.MainRepository
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.Util.EspressoIdlingResource
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    lateinit var vm: MainViewModel

    @Inject
    lateinit var repo: MainRepository

    private var mIdlingResource: IdlingResource? = null


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

//    @ExperimentalCoroutinesApi
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun cleanUp() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister()
        }
    }

    //    @Test
//    fun get_random_books_test() {
//        vm = MainViewModel(repo)
//
//        vm.getRandomBooks()
//        vm.FetchBookList.getOrAwaitValue()
//
//        val result = vm.FetchBookList.value
//        assert(result != null)
//        assert(result!!.isNotEmpty())
//        assert(result.size > 10)
//    }
//
//    @Test
//    fun add_Favorite_test() {
//        vm = MainViewModel(repo)
//
//        val book = Book(123123, "test 1 title", "test author", "testurl11")
//        val book2 = Book(123124, "test 2 title", "test2 author", "testurl22")
//        val book3 = Book(123125, "test 3 title", "test3 author", "testurl33")
//
//        vm.addFavorite(book)
//        vm.FavoriteBookList.getOrAwaitValue()
//        vm.addFavorite(book2)
//        vm.FavoriteBookList.getOrAwaitValue()
//        vm.addFavorite(book3)
//        vm.FavoriteBookList.getOrAwaitValue()
//        vm.getFavorite()
//        vm.FavoriteBookList.getOrAwaitValue()
//
//        val result = vm.FavoriteBookList.value
//        assert(result != null)
//        assert(result!!.isNotEmpty())
//        assert(result[0].id == book.id)
//        assert(result[0].title == book.title)
//        assert(result[0].authorname == book.authorname)
//        assert(result[0].imgurl == book.imgurl)
//
//        assert(result[1].id == book2.id)
//        assert(result[1].title == book2.title)
//        assert(result[1].authorname == book2.authorname)
//        assert(result[1].imgurl == book2.imgurl)
//
//        assert(result[2].id == book3.id)
//        assert(result[2].title == book3.title)
//        assert(result[2].authorname == book3.authorname)
//        assert(result[2].imgurl == book3.imgurl)
//    }
//
//    @Test
//    fun delete_Favorite_test() {
//        vm = MainViewModel(repo)
//
//        val book = Book(123123, "test 1 title", "test author", "testurl11")
//
//        vm.addFavorite(book)
//        vm.FavoriteBookList.getOrAwaitValue()
//
//        vm.deleteFavorite(book)
//        vm.FavoriteBookList.getOrAwaitValue()
//
//        assert(vm.FavoriteBookList.value == null)
//    }
//
    @Test
    fun delete_Favorite_inside_test() {
        vm = MainViewModel(repo)

        val book = Book(123123, "test 1 title", "test author", "testurl11")
        val book2 = Book(123124, "test 2 title", "test2 author", "testurl22")
        val book3 = Book(123125, "test 3 title", "test3 author", "testurl33")

        vm.addFavorite(book)
        vm.FavoriteBookList.getOrAwaitValue()
        Log.d("MYTAG", "add fav 1 - ${vm.FavoriteBookList.value}")
        vm.addFavorite(book2)
        vm.FavoriteBookList.getOrAwaitValue()
        Log.d("MYTAG", "add fav 2 - ${vm.FavoriteBookList.value}")
        vm.addFavorite(book3)
        vm.FavoriteBookList.getOrAwaitValue()
        Log.d("MYTAG", "add fav 3 - ${vm.FavoriteBookList.value}")

        vm.deleteFavorite(book2)
        vm.FavoriteBookList.getOrAwaitValue()
        Log.d("MYTAG", "del fav - ${vm.FavoriteBookList.value}")

        vm.getFavorite()
        vm.FavoriteBookList.getOrAwaitValue()
        Log.d("MYTAG", "get fav - ${vm.FavoriteBookList.value}")

        val result = vm.FavoriteBookList.value
        Log.d("MYTAG", "ret - ${result}")

        assert(result != null)
        assert(result!!.isNotEmpty())
        assert(result.size == 2)
        assert(result[0].id == book.id)
        assert(result[0].title == book.title)
        assert(result[0].authorname == book.authorname)
        assert(result[0].imgurl == book.imgurl)

        assert(result[1].id == book3.id)
        assert(result[1].title == book3.title)
        assert(result[1].authorname == book3.authorname)
        assert(result[1].imgurl == book3.imgurl)
    }

    @Test
    fun get_Favorite_Cache_test() {
        vm = MainViewModel(repo)

        val book = Book(123123, "test 1 title", "test author", "testurl11")
        val book2 = Book(123124, "test 2 title", "test2 author", "testurl22")
        val book3 = Book(123125, "test 3 title", "test3 author", "testurl33")

        vm.addFavorite(book)
        vm.FavoriteBookList.getOrAwaitValue()
        Log.d("MYTAG", "add fav 1 - ${vm.FavoriteBookList.value}")
        vm.addFavorite(book2)
        vm.FavoriteBookList.getOrAwaitValue()
        Log.d("MYTAG", "add fav 2 - ${vm.FavoriteBookList.value}")
        vm.addFavorite(book3)
        vm.FavoriteBookList.getOrAwaitValue()
        Log.d("MYTAG", "add fav 3 - ${vm.FavoriteBookList.value}")

        vm.deleteFavorite(book2)
        vm.FavoriteBookList.getOrAwaitValue()
        Log.d("MYTAG", "del fav - ${vm.FavoriteBookList.value}")

        val result = vm.getFavoriteCache()
        Log.d("MYTAG", "ret - ${result}")

        assert(result.isNotEmpty())
        assert(result.size == 2)
        assert(result[0].id == book.id)
        assert(result[0].title == book.title)
        assert(result[0].authorname == book.authorname)
        assert(result[0].imgurl == book.imgurl)

        assert(result[1].id == book3.id)
        assert(result[1].title == book3.title)
        assert(result[1].authorname == book3.authorname)
        assert(result[1].imgurl == book3.imgurl)
    }
}