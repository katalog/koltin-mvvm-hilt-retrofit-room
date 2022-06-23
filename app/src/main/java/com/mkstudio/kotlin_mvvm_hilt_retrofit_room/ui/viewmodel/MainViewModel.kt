package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {
    private val _fetchBookList = MutableLiveData<List<Book>>()
    val FetchBookList: LiveData<List<Book>> get() = _fetchBookList
    private val _favoriteBookList = MutableLiveData<MutableList<Book>>()
    val FavoriteBookList: LiveData<MutableList<Book>> get() = _favoriteBookList

    fun getRandomBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.getRandomBooks()
            list.forEach {
                Log.d("MYTAG", it.toString())
            }

            _fetchBookList.postValue(list)
        }
    }

    fun addFavorite(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            var b = book
            b.favorite = true

            val favList = _favoriteBookList.value
            favList?.let { it.add(b) }
            _favoriteBookList.postValue(favList)

            repo.insertBook(b)
            Log.d("MYTAG", "add fav $b")
        }
    }

    fun deleteFavorite(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            var b = book
            val favList = _favoriteBookList.value
            favList?.let { it.remove(b) }
            _favoriteBookList.postValue(favList)

            repo.deleteBook(b.id)
            Log.d("MYTAG", "del fav $b")
        }
    }

    fun getFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            val favbooks = repo.getAllBooks()
            _favoriteBookList.postValue(favbooks as MutableList<Book>)
            Log.d("MYTAG", "get fav $favbooks")
        }
    }

    fun getFavoriteCache(): List<Book> {
        return FavoriteBookList.value as List<Book>
    }
}