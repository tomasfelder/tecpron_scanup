package com.tecpron.tecpronscanning.dagger

import android.app.Application
import android.content.Context
import com.tecpron.tecpronscanning.data.db.Database
import com.tecpron.tecpronscanning.data.db.DatabaseImpl
import com.tecpron.tecpronscanning.data.db.StationDao
import com.tecpron.tecpronscanning.data.db.StationDaoImpl
import com.tecpron.tecpronscanning.data.network.ClientFactory
import com.tecpron.tecpronscanning.data.network.StationDataSourceWithDataStoreImpl
import com.tecpron.tecpronscanning.data.network.StationsDataSource
import com.tecpron.tecpronscanning.data.repository.StationRepository
import com.tecpron.tecpronscanning.data.repository.StationRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideStationDao(): StationDao = StationDaoImpl()

    @Singleton
    @Provides
    fun provideDatabase(stationDao: StationDao): Database = DatabaseImpl(stationDao)

    @Singleton
    @Provides
    fun provideStationDataSource(): StationsDataSource = StationDataSourceWithDataStoreImpl()

    @Singleton
    @Provides
    fun provideStationRepository(stationDao: StationDao,stationsDataSource : StationsDataSource) : StationRepository = StationRepositoryImpl(stationDao, stationsDataSource)
}