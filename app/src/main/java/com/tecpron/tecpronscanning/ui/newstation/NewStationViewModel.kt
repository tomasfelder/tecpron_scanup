package com.tecpron.tecpronscanning.ui.newstation

import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.Station
import com.tecpron.tecpronscanning.data.repository.StationRepository
import com.tecpron.tecpronscanning.general.lazyDeferred
import javax.inject.Inject

class NewStationViewModel @Inject constructor(private val stationRepository: StationRepository) : ViewModel() {

    suspend fun addStation(station: Station){
        stationRepository.addStation(station)
    }

    suspend fun updateStation(station: Station){
        stationRepository.updateStation(station)
    }

    val notes by lazyDeferred {
        stationRepository.getNotes()
    }

    val projects by lazyDeferred {
        stationRepository.getLeicaScanningProjectsByTecpronProject("")
    }
}
