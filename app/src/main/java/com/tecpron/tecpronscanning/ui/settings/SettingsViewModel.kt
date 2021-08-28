package com.tecpron.tecpronscanning.ui.settings

import androidx.lifecycle.ViewModel
import com.tecpron.tecpronscanning.data.repository.StationRepository
import com.tecpron.tecpronscanning.general.lazyDeferred
import javax.inject.Inject

class SettingsViewModel  @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel() {
    val tecpronProjects by lazyDeferred {
        stationRepository.getTecpronProjects()
    }

}
