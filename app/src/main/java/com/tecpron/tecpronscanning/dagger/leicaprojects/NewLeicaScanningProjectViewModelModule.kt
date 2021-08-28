package com.tecpron.tecpronscanning.dagger.leicaprojects

import androidx.lifecycle.ViewModel
import com.tecpron.tecpronscanning.dagger.ViewModelKey
import com.tecpron.tecpronscanning.ui.leicaprojects.NewLeicaScanningProjectViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NewLeicaScanningProjectViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewLeicaScanningProjectViewModel::class)
    abstract fun bindNewLeicaScanningProjectViewModel(viewModel: NewLeicaScanningProjectViewModel?): ViewModel?
}