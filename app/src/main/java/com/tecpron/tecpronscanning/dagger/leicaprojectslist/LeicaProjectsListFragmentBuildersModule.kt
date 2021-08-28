package com.tecpron.tecpronscanning.dagger.leicaprojectslist

import com.tecpron.tecpronscanning.ui.leicaprojectslist.LeicaProjectsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LeicaProjectsListFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeLeicaProjectsListFragment(): LeicaProjectsListFragment
}