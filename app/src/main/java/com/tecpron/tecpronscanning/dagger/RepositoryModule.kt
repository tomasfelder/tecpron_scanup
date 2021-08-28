package com.tecpron.tecpronscanning.dagger

import com.tecpron.tecpronscanning.data.db.StationDao
import com.tecpron.tecpronscanning.data.network.StationsDataSource
import com.tecpron.tecpronscanning.data.repository.StationRepository
import com.tecpron.tecpronscanning.data.repository.StationRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideStationRepository(stationDao: StationDao,stationsDataSource : StationsDataSource) : StationRepository = StationRepositoryImpl(stationDao, stationsDataSource)

}