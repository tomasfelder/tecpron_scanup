package com.tecpron.tecpronscanning.dagger

import com.tecpron.tecpronscanning.MainActivity
import com.tecpron.tecpronscanning.dagger.leicaprojects.NewLeicaScanningProjectFragmentBuildersModule
import com.tecpron.tecpronscanning.dagger.leicaprojects.NewLeicaScanningProjectViewModelModule
import com.tecpron.tecpronscanning.dagger.leicaprojectslist.LeicaProjectsListFragmentBuildersModule
import com.tecpron.tecpronscanning.dagger.leicaprojectslist.LeicaProjectsListViewModelModule
import com.tecpron.tecpronscanning.dagger.map.MapFragmentBuildersModule
import com.tecpron.tecpronscanning.dagger.map.MapViewModelModule
import com.tecpron.tecpronscanning.dagger.newstation.NewStationFragmentBuildersModule
import com.tecpron.tecpronscanning.dagger.newstation.NewStationViewModelModule
import com.tecpron.tecpronscanning.dagger.settings.SettingsFragmentBuildersModule
import com.tecpron.tecpronscanning.dagger.settings.SettingsViewModelModule
import com.tecpron.tecpronscanning.dagger.stationslist.StationsListFragmentBuildersModule
import com.tecpron.tecpronscanning.dagger.stationslist.StationsListViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [StationsListFragmentBuildersModule::class,
                    NewStationFragmentBuildersModule::class,
                    LeicaProjectsListFragmentBuildersModule::class,
                    MapFragmentBuildersModule::class,
                    SettingsFragmentBuildersModule::class,
                    NewLeicaScanningProjectFragmentBuildersModule::class,
                    NewLeicaScanningProjectViewModelModule::class,
                    SettingsViewModelModule::class,
                    MapViewModelModule::class,
                    LeicaProjectsListViewModelModule::class,
                    StationsListViewModelModule::class,
                    NewStationViewModelModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}