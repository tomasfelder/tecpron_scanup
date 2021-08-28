package com.tecpron.tecpronscanning.data.repository

import androidx.lifecycle.LiveData
import com.amplifyframework.datastore.generated.model.*
import com.tecpron.tecpronscanning.data.db.StationDao
import com.tecpron.tecpronscanning.data.model.InternalScanningProject

import com.tecpron.tecpronscanning.data.network.StationsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StationRepositoryImpl(
    private val stationDao: StationDao,
    private val stationsDataSource : StationsDataSource
    ) : StationRepository {

    override suspend fun getLeicaScanningProjectsByTecpronProject(id: String): LiveData<out List<InternalScanningProject>> {
        return withContext(Dispatchers.IO) {
            if(id.isNotEmpty())
                stationsDataSource.fetchLeicaScanningProjects(id)
            return@withContext stationsDataSource.downloadedProjects
        }
    }

    override suspend fun getTecpronProjects(): LiveData<out List<TecpronProject>> {
        return withContext(Dispatchers.IO) {
            stationsDataSource.fetchTecpronProjects()
            return@withContext stationsDataSource.downloadadTecpronProjects
        }
    }

    override suspend fun addStation(station: Station) {
        stationsDataSource.addStation(station)
    }

    override suspend fun updateStation(station: Station) {
        stationsDataSource.updateStation(station)
    }

    override suspend fun getStationsByTecpronProject(id: String): LiveData<out List<Station>> {
        return withContext(Dispatchers.IO) {
            if(id.isNotEmpty())
                stationsDataSource.fetchStations(id)
            return@withContext stationsDataSource.downloadadStations
        }
    }

    override suspend fun getNotes(): LiveData<out List<Note>> {
        return withContext(Dispatchers.IO) {
            stationsDataSource.fetchNotes()
            return@withContext stationsDataSource.downloadedNotes
        }
    }

    override suspend fun addLeicaScanningProject(leicaScanningProject: LeicaScanningProject) {
        stationsDataSource.addLeicaScanningProject(leicaScanningProject)
    }

    override suspend fun getScanners(): LiveData<out List<Scanner>> {
        return withContext(Dispatchers.IO) {
            stationsDataSource.fetchScanners()
            return@withContext stationsDataSource.downloadedScanners
        }
    }

}