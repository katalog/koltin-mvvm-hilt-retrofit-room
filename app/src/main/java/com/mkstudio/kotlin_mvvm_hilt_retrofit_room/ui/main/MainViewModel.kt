package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val api:APIService) : ViewModel() {
    fun getBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = api.getBookList("1411")
            list.forEach {
                Log.d("MYTAG", it.toString())
            }
        }
    }
}