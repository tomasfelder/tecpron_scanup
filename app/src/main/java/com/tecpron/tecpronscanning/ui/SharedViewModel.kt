package com.tecpron.tecpronscanning.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel(){
    val mapPoint = MutableLiveData<List<Int>>(mutableListOf())
}