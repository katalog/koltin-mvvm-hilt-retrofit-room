package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.Util

import androidx.lifecycle.MutableLiveData

// https://stackoverflow.com/questions/44582019/how-to-clear-livedata-stored-value

class SelfCleaningLiveData<T>: MutableLiveData<T>() {
    override fun onInactive() {
        super.onInactive()
        value = null
    }
}