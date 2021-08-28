package com.tecpron.tecpronscanning.dagger.newstation

import androidx.lifecycle.ViewModel
import com.tecpron.tecpronscanning.dagger.ViewModelKey
import com.tecpron.tecpronscanning.ui.newstation.NewStationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class NewStationViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewStationViewModel::class)
    abstract fun bindNewStationViewModel(viewModel: NewStationViewModel?): ViewModel?
}