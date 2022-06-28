package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo.MainRepository
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.Util.SelfCleaningLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBooksViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {
    private val _searchBookList = MutableLiveData<List<Book>>()
    val SearchBookList: LiveData<List<Book>> get() = _searchBookList
    private val _searchFailed = SelfCleaningLiveData<Boolean>()
    val SearchFailed: LiveData<Boolean> get() = _searchFailed

    fun searchBook(searchquery: String) {
        viewModelScope.launch {
            val searchBooks = repo.searchBooks(searchquery)
            if ( searchBooks.isNotEmpty() )  {
                _searchBookList.postValue(searchBooks)
            } else {
                _searchFailed.postValue(true)
            }
        }
    }
}

