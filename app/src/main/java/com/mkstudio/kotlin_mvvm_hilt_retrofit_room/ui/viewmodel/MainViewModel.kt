package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.Repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo:MainRepository) : ViewModel() {
    private val _booklist = MutableLiveData<List<Book>>()
    val Booklist: LiveData<List<Book>> get() = _booklist

    fun getRandomBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.getRandomBooks()
            list.forEach {
                Log.d("MYTAG", it.toString())
            }

            _booklist.postValue(list)
        }
    }
}