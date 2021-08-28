package com.tecpron.tecpronscanning.dagger.stationslist

import androidx.lifecycle.ViewModel
import com.tecpron.tecpronscanning.dagger.ViewModelKey
import com.tecpron.tecpronscanning.ui.stationslist.StationsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StationsListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(StationsListViewModel::class)
    abstract fun bindStationsListViewModel(viewModel: StationsListViewModel?): ViewModel?
}