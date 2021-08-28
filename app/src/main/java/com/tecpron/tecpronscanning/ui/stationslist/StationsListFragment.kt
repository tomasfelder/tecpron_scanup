package com.tecpron.tecpronscanning.ui.stationslist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.Station
import com.tecpron.tecpronscanning.R
import com.tecpron.tecpronscanning.ui.ViewModelProviderFactory
import com.tecpron.tecpronscanning.ui.base.ScopedFragment
import com.tecpron.tecpronscanning.ui.leicaprojectslist.StationListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.stations_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class StationsListFragment : ScopedFragment() {

    private lateinit var viewModel: StationsListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private var stationsList: List<Station> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stations_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StationsListViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        /*
        val stations = viewModel.stations.await()

        stations.observe(this@StationsListFragment, Observer { stationResponse ->
            if (stationResponse == null) return@Observer
            stationsList = stationResponse
            initRecyclerView(stationResponse.toStationItems())
        })

        add_station.setOnClickListener {
            val action = StationsListFragmentDirections.actionStationsListToNewStation(stationsList.size+1)
            view?.findNavController()?.navigate(action)
        }

         */
    }

    private fun List<Station>.toStationItems() : List<StationListItem> {
        return this.map {
            StationListItem(it)
        }
    }

    private fun initRecyclerView(items: List<StationListItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerViewStations.apply {
            layoutManager = LinearLayoutManager(this@StationsListFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, _ ->
            (item as? StationListItem)?.let {
                editStation(item.station)
            }
        }
    }

    private fun editStation(station: Station) {
        /*
        val action = StationsListFragmentDirections.actionStationsListToNewStation(station.stationNumber,station)
        view?.findNavController()?.navigate(action)

         */
    }

}
