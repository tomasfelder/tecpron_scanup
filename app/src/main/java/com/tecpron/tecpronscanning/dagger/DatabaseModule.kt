package com.tecpron.tecpronscanning.dagger

import com.tecpron.tecpronscanning.data.db.Database
import com.tecpron.tecpronscanning.data.db.DatabaseImpl
import com.tecpron.tecpronscanning.data.db.StationDao
import com.tecpron.tecpronscanning.data.db.StationDaoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideStationDao(): StationDao = StationDaoImpl()

    @Provides
    @Singleton
    fun provideDatabase(stationDao: StationDao): Database = DatabaseImpl(stationDao)

}