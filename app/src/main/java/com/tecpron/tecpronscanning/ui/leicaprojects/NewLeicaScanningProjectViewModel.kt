package com.tecpron.tecpronscanning.ui.leicaprojects

import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.LeicaScanningProject
import com.amplifyframework.datastore.generated.model.Station
import com.tecpron.tecpronscanning.data.repository.StationRepository
import com.tecpron.tecpronscanning.general.lazyDeferred
import javax.inject.Inject

class NewLeicaScanningProjectViewModel @Inject constructor(private val stationRepository: StationRepository) : ViewModel() {

    suspend fun addLeicaScanningProject(leicaScanningProject: LeicaScanningProject){
        stationRepository.addLeicaScanningProject(leicaScanningProject)
    }

    val scanners by lazyDeferred {
        stationRepository.getScanners()
    }
}
