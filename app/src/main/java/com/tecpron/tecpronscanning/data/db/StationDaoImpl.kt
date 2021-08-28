package com.tecpron.tecpronscanning.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tecpron.tecpronscanning.data.model.Station

class StationDaoImpl : StationDao {

    private val stationList = mutableListOf<Station>()
    private val stations = MutableLiveData<List<Station>>()

    init {
        stations.value = stationList
    }

    override fun addStation(station: Station) {
        stationList.add(station)
        stations.value = stationList
    }

    override fun getStations() = stations as LiveData<List<Station>>
}