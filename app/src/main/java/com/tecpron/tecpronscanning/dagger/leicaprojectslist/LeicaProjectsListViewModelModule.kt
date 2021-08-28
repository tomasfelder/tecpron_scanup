package com.tecpron.tecpronscanning.dagger.leicaprojectslist

import androidx.lifecycle.ViewModel
import com.tecpron.tecpronscanning.dagger.ViewModelKey
import com.tecpron.tecpronscanning.ui.leicaprojectslist.LeicaProjectsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class LeicaProjectsListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LeicaProjectsListViewModel::class)
    abstract fun bindLeicaProjectsListViewModel(viewModel: LeicaProjectsListViewModel?): ViewModel?
}