package com.tecpron.tecpronscanning.dagger.settings

import com.tecpron.tecpronscanning.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment
}