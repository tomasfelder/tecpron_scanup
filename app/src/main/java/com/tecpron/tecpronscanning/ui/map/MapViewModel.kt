package com.tecpron.tecpronscanning.ui.map

import androidx.lifecycle.ViewModel
import com.tecpron.tecpronscanning.data.repository.StationRepository
import com.tecpron.tecpronscanning.general.lazyDeferred
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val stationRepository: StationRepository
)  : ViewModel() {

    private var tecpronProjectId: String = "1d3ab7ec-8329-40c6-934c-9dbdb76e2d86"

    fun setTecpronProjectId(id: String){
        tecpronProjectId = id
    }

    val stations by lazyDeferred {
        stationRepository.getStationsByTecpronProject(tecpronProjectId)
    }

}
