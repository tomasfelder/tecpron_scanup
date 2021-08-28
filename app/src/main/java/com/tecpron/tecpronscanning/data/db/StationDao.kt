package com.tecpron.tecpronscanning.data.db

import androidx.lifecycle.LiveData
import com.tecpron.tecpronscanning.data.model.Station

interface StationDao {
    fun addStation(station: Station)
    fun getStations(): LiveData<List<Station>>
}