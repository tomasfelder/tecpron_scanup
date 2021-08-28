package com.tecpron.tecpronscanning.dagger.stationslist

import com.tecpron.tecpronscanning.ui.stationslist.StationsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StationsListFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeStationsListFragment(): StationsListFragment
}