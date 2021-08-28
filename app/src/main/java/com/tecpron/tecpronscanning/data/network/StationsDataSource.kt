package com.tecpron.tecpronscanning.data.network

import androidx.lifecycle.LiveData
import com.amplifyframework.datastore.generated.model.*
import com.tecpron.tecpronscanning.data.model.InternalScanningProject

interface StationsDataSource {
    val downloadadStations: LiveData<List<Station>>
    val downloadadTecpronProjects: LiveData<List<TecpronProject>>
    val downloadedProjects: LiveData<List<InternalScanningProject>>
    val downloadedNotes: LiveData<List<Note>>
    val downloadedScanners: LiveData<List<Scanner>>

    suspend fun fetchStations(id: String)
    suspend fun fetchNotes()
    suspend fun fetchLeicaScanningProjects(id: String)
    suspend fun fetchScanners()
    suspend fun fetchTecpronProjects()
    suspend fun addStation(station: Station)
    suspend fun updateStation(station: Station)
    suspend fun addLeicaScanningProject(leicaScanningProject: LeicaScanningProject)

}