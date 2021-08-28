package com.tecpron.tecpronscanning.dagger.map

import androidx.lifecycle.ViewModel
import com.tecpron.tecpronscanning.dagger.ViewModelKey
import com.tecpron.tecpronscanning.ui.map.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MapViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(viewModel: MapViewModel?): ViewModel?
}