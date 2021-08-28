package com.tecpron.tecpronscanning.dagger

import android.content.Context
import com.tecpron.tecpronscanning.data.network.ClientFactory
import com.tecpron.tecpronscanning.data.network.StationDataSourceWithDataStoreImpl
import com.tecpron.tecpronscanning.data.network.StationsDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideStationDataSource(): StationsDataSource = StationDataSourceWithDataStoreImpl()

}