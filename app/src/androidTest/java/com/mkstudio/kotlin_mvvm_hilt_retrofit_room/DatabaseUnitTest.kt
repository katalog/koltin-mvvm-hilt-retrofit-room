package com.mkstudio.kotlin_mvvm_hilt_retrofit_room

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo.MainRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DatabaseUnitTest {

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: MainRepository

    @Before
    fun setUp() {
        hiltrule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun book_insert_test() = runTest {
        repository.insertBook(book = Book(131, "title1", "author1", "http://myimg.net/1.jpg"))
        val ret = repository.getBook(131)

        assert(ret != null)
        assert(ret?.id == 131)
        assert(ret?.title == "title1")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun book_update_test() = runTest {
        repository.insertBook(book = Book(131, "title1", "author1", "http://myimg.net/1.jpg"))
        repository.insertBook(book = Book(131, "title2", "author1", "http://myimg.net/1.jpg"))

        val ret = repository.getBook(131)

        assert(ret != null)
        assert(ret?.title == "title2")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun book_multi_insert_test() = runTest {
        repository.insertBook(book = Book(131, "title1", "author1", "http://myimg.net/1.jpg"))
        repository.insertBook(book = Book(132, "title2", "author2", "http://myimg.net/2.jpg"))
        repository.insertBook(book = Book(133, "title3", "author3", "http://myimg.net/3.jpg"))
        repository.insertBook(book = Book(134, "title4", "author4", "http://myimg.net/4.jpg"))

        var ret = repository.getBook(131)
        assert(ret != null)
        assert(ret?.title == "title1")

        ret = repository.getBook(132)
        assert(ret != null)
        assert(ret?.title == "title2")

        ret = repository.getBook(133)
        assert(ret != null)
        assert(ret?.title == "title3")

        ret = repository.getBook(134)
        assert(ret != null)
        assert(ret?.title == "title4")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun book_remove_test() = runTest {
        repository.insertBook(book = Book(131, "title1", "author1", "http://myimg.net/1.jpg"))
        repository.deleteBook(131)

        val ret = repository.getBook(131)

        assert(ret == null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun book_remove_wrong_test() = runTest {
        repository.insertBook(book = Book(131, "title1", "author1", "http://myimg.net/1.jpg"))
        repository.deleteBook(132)

        var ret = repository.getBook(132)
        assert(ret == null)

        ret = repository.getBook(131)
        assert(ret != null)
        assert(ret?.title == "title1")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun book_find_fail() = runTest {
        repository.insertBook(book = Book(131, "title1", "author1", "http://myimg.net/1.jpg"))

        val ret = repository.getBook(132)

        assert(ret == null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun book_getall_test() = runTest {
        repository.insertBook(book = Book(131, "title1", "author1", "http://myimg.net/1.jpg"))
        repository.insertBook(book = Book(132, "title2", "author2", "http://myimg.net/2.jpg"))
        repository.insertBook(book = Book(133, "title3", "author3", "http://myimg.net/3.jpg"))
        repository.insertBook(book = Book(134, "title4", "author4", "http://myimg.net/4.jpg"))

        val ret = repository.getAllBooks()

        assert(ret.isNotEmpty())
        assert(ret.size == 4)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun book_getall_empty_test() = runTest {
        val ret = repository.getAllBooks()
        assert(ret.isEmpty())
    }

    fun onViewWithTimeout(
        retries: Int = 10,
        retryDelayMs: Long = 500,
        retryAssertion: ViewAssertion = matches(withEffectiveVisibility(Visibility.VISIBLE)),
        matcher: Matcher<View>
    ): ViewInteraction {
        repeat(retries) { i ->
            try {
                val viewInteraction = Espresso.onView(matcher)
                viewInteraction.check(retryAssertion)
                return viewInteraction
            } catch (e: NoMatchingViewException) {
                if (i >= retries) {
                    throw e
                } else {
                    Thread.sleep(retryDelayMs)
                }
            }
        }
        throw AssertionError("View matcher is broken for $matcher")
    }
}