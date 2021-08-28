package com.tecpron.tecpronscanning.ui.stationslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecpron.tecpronscanning.data.repository.StationRepository

class StationsListViewModelFactory(
    private val stationsRepository: StationRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StationsListViewModel(
            stationsRepository
        ) as T
    }
}