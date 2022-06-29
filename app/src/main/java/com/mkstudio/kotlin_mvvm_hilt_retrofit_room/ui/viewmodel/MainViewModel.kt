package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {
    private val _fetchBookList = MutableLiveData<List<Book>>()
    val FetchBookList: LiveData<List<Book>> get() = _fetchBookList
    private val _favoriteBookList = MutableLiveData<MutableList<Book>>()
    val FavoriteBookList: LiveData<MutableList<Book>> get() = _favoriteBookList

    fun getRandomBooks() {
        viewModelScope.launch {
            val list = repo.getRandomBooks()
            if (list.isNotEmpty()) {
                _fetchBookList.postValue(list)
            }
        }
    }

    fun addFavorite(book: Book) {
        viewModelScope.launch {
            var b = book
            b.favorite = true

            var favList = _favoriteBookList.value
            favList = favList ?: mutableListOf<Book>()
            favList!!.add(b)
            _favoriteBookList.postValue(favList)

            repo.insertBook(b)
        }
    }

    fun deleteFavorite(book: Book) {
        viewModelScope.launch {
            var b = book
            val favList = _favoriteBookList.value
            favList?.let { it.remove(b) }
            _favoriteBookList.postValue(favList)

            repo.deleteBook(b.id)
        }
    }

    fun getFavorite() {
        viewModelScope.launch {
            val favbooks = repo.getAllBooks()
            _favoriteBookList.postValue(favbooks as MutableList<Book>)
        }
    }

    fun getFavoriteCache(): MutableList<Book> {
        return FavoriteBookList.value as MutableList<Book>
    }
}