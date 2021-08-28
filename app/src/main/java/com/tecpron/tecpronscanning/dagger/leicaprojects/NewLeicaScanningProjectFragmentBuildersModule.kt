package com.tecpron.tecpronscanning.dagger.leicaprojects

import com.tecpron.tecpronscanning.ui.leicaprojects.NewLeicaScanningProjectFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewLeicaScanningProjectFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeNewLeicaScanningProjectFragment(): NewLeicaScanningProjectFragment
}