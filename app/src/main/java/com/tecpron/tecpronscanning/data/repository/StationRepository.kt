package com.tecpron.tecpronscanning.data.repository

import androidx.lifecycle.LiveData
import com.amplifyframework.datastore.generated.model.*
import com.tecpron.tecpronscanning.data.model.InternalScanningProject


interface StationRepository {
    suspend fun getLeicaScanningProjectsByTecpronProject(id: String): LiveData<out List<InternalScanningProject>>
    suspend fun addStation(station: Station)
    suspend fun updateStation(station: Station)
    suspend fun getStationsByTecpronProject(id: String): LiveData<out List<Station>>
    suspend fun getNotes(): LiveData<out List<Note>>
    suspend fun addLeicaScanningProject(leicaScanningProject: LeicaScanningProject)
    suspend fun getScanners(): LiveData<out List<Scanner>>
    suspend fun getTecpronProjects(): LiveData<out List<TecpronProject>>
}