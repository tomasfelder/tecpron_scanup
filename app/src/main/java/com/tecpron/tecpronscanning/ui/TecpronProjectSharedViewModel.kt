package com.tecpron.tecpronscanning.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.TecpronProject

class TecpronProjectSharedViewModel : ViewModel() {
    val selected = MutableLiveData<TecpronProject>()
    val tecpronMap = MutableLiveData<String>()

    fun select(tecpronProject: TecpronProject) {
        selected.value = tecpronProject
    }

}