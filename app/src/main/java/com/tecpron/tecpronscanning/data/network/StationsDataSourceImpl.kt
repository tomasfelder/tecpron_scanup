package com.tecpron.tecpronscanning.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.*
import com.amplifyframework.datastore.generated.model.Scanner
import com.tecpron.tecpronscanning.data.model.InternalScanner
import com.tecpron.tecpronscanning.data.model.InternalScanningProject

class StationDataSourceWithImpl : StationsDataSource {
    private val _downloadedStations = MutableLiveData<List<Station>>()
    override val downloadadStations: LiveData<List<Station>>
        get() = _downloadedStations

    private val _downloadedProjects = MutableLiveData<List<InternalScanningProject>>()
    override val downloadedProjects: LiveData<List<InternalScanningProject>>
        get() = _downloadedProjects

    private val _downloadedNotes = MutableLiveData<List<Note>>()
    override val downloadedNotes: LiveData<List<Note>>
        get() = _downloadedNotes

    private val _downloadedScanners = MutableLiveData<List<Scanner>>()
    override val downloadedScanners: LiveData<List<Scanner>>
        get() = _downloadedScanners

    private val _downloadadTecpronProjects = MutableLiveData<List<TecpronProject>>()
    override val downloadadTecpronProjects: LiveData<List<TecpronProject>>
        get() = _downloadadTecpronProjects

    override suspend fun fetchStations(id: String) {
        Amplify.API.query(
            ModelQuery.list(Station::class.java),
            { response ->
                val stationList = mutableListOf<Station>()
                for (station in response.data) {
                    stationList.add(station)
                }
                stationList.sortBy { x -> x.stationNumber }
                _downloadedStations.postValue(stationList)
            },
            { error -> Log.e("MyAmplifyApp", "Query failed", error) }
        )

    }

    override suspend fun fetchNotes() {
        Amplify.API.query(
            ModelQuery.list(Note::class.java),
            { response ->
                val notesList = mutableListOf<Note>()
                for (note in response.data) {
                    notesList.add(note)
                }
                notesList.sortBy { x -> x.name }
                _downloadedNotes.postValue(notesList)
            },
            { error -> Log.e("MyAmplifyApp", "Query failure", error) }
        )
    }

    override suspend fun fetchLeicaScanningProjects(id: String) {
        Amplify.API.query(ModelQuery.list(LeicaScanningProject::class.java),
            {
                response ->
                val projectsList = mutableListOf<InternalScanningProject>()
                for (leicaScanningProject in response.data) {
                    val date = leicaScanningProject.startDate
                    val stationsSize = leicaScanningProject.stations?.size!!
                    val internalLeicaScanningProject = InternalScanningProject(leicaScanningProject.id,leicaScanningProject.name,date,stationsSize, InternalScanner(leicaScanningProject.scanner.id,leicaScanningProject.scanner.name!!))
                    projectsList.add(internalLeicaScanningProject)
                }
                projectsList.sortBy { x -> x.name }
                _downloadedProjects.postValue(projectsList)
            },
            { Log.e("MyAmplifyApp", "Query failed.", it) }
        )
    }

    override suspend fun fetchScanners() {
        Amplify.API.query(
            ModelQuery.list(Scanner::class.java),
            { response ->
                val scannerList = mutableListOf<Scanner>()
                for (scanner in response.data) {
                    scannerList.add(scanner)
                }
                scannerList.sortBy { x -> x.name }
                _downloadedScanners.postValue(scannerList)
            },
            { error -> Log.e("MyAmplifyApp", "Query failure", error) }
        )
    }

    override suspend fun fetchTecpronProjects() {
        Amplify.API.query(
            ModelQuery.list(TecpronProject::class.java),
            { response ->
                val tecpronProjectList = mutableListOf<TecpronProject>()
                for (tecpronProject in response.data) {
                    tecpronProjectList.add(tecpronProject)
                }
                tecpronProjectList.sortBy { x -> x.name }
                _downloadadTecpronProjects.postValue(tecpronProjectList)
            },
            { error -> Log.e("MyAmplifyApp", "Query failure", error) }
        )
    }

    override suspend fun addStation(station: Station) {
        Amplify.API.mutate(
            ModelMutation.create(station.scannerConfiguration),
            { response1 ->
                Amplify.API.mutate(
                    ModelMutation.create(station),
                    { response2 ->
                        Log.i("MyAmplifyApp", "Created Station with id: " + response2.data.id) },
                    { error -> Log.e("MyAmplifyApp", "Station failed", error) }
                )
                Log.i("MyAmplifyApp", "Created ScannerConfig with id: " + response1.data.id) },
            { error -> Log.e("MyAmplifyApp", "Create ScannerConfig failed", error) }
        )
    }

    override suspend fun updateStation(station: Station) {
        Amplify.API.mutate(
            ModelMutation.update(station.scannerConfiguration),
            { response1 ->
                Amplify.API.mutate(
                    ModelMutation.update(station),
                    { response2 ->
                        Log.i("MyAmplifyApp", "Updated Station with id: " + response2.data.id) },
                    { error -> Log.e("MyAmplifyApp", "Updated Station failed", error) }
                )
                Log.i("MyAmplifyApp", "Updated ScannerConfig with id: " + response1.data.id) },
            { error -> Log.e("MyAmplifyApp", "Update failed", error) }
        )
    }

    override suspend fun addLeicaScanningProject(leicaScanningProject: LeicaScanningProject) {
        Amplify.API.mutate(
            ModelMutation.create(leicaScanningProject),
            { response -> Log.i("MyAmplifyApp", "Created LeicaScanningProject with id: " + response.data.id) },
            { error -> Log.e("MyAmplifyApp", "LeicaScanningProject failed", error) }
        )
    }
}