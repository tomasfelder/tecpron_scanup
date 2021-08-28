package com.tecpron.tecpronscanning.ui.stationslist

import androidx.lifecycle.ViewModel
import com.tecpron.tecpronscanning.data.repository.StationRepository
import com.tecpron.tecpronscanning.general.lazyDeferred
import javax.inject.Inject

class StationsListViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel() {

    val stations by lazyDeferred {

    }
}