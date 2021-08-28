package com.tecpron.tecpronscanning.dagger.newstation

import com.tecpron.tecpronscanning.ui.newstation.NewStationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewStationFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeStationsListFragment(): NewStationFragment
}