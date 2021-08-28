package com.tecpron.tecpronscanning.dagger

import androidx.lifecycle.ViewModelProvider
import com.tecpron.tecpronscanning.ui.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory?): ViewModelProvider.Factory?
}