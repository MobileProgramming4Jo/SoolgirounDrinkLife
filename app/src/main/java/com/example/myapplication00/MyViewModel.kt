package com.example.myapplication00

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val selectedDate = MutableLiveData<String>()

    fun setLiveData(date : String) : String {
        selectedDate.value = date
        return date
    }
}