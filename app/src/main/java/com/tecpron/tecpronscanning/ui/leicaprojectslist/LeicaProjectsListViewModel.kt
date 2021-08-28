package com.tecpron.tecpronscanning.ui.leicaprojectslist

import androidx.lifecycle.ViewModel
import com.tecpron.tecpronscanning.data.repository.StationRepository
import com.tecpron.tecpronscanning.general.lazyDeferred
import javax.inject.Inject

class LeicaProjectsListViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel() {

    private var tecpronProjectId: String = ""

    val projects by lazyDeferred {
        stationRepository.getLeicaScanningProjectsByTecpronProject(tecpronProjectId)
    }

    fun setTecpronProjectId(id: String){
        tecpronProjectId = id
    }

    val stations by lazyDeferred {
        stationRepository.getStationsByTecpronProject(tecpronProjectId)
    }

}