package com.tecpron.tecpronscanning.ui.newstation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecpron.tecpronscanning.data.repository.StationRepository

class StationViewModelFactory(private val stationRepository: StationRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewStationViewModel(stationRepository) as T
    }

}