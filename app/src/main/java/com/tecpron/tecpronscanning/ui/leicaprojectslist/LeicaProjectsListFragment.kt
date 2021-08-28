package com.tecpron.tecpronscanning.ui.leicaprojectslist

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.Station
import com.amplifyframework.datastore.generated.model.TecpronProject
import com.tecpron.tecpronscanning.MainActivity
import com.tecpron.tecpronscanning.R
import com.tecpron.tecpronscanning.data.model.InternalScanningProject
import com.tecpron.tecpronscanning.ui.TecpronProjectSharedViewModel
import com.tecpron.tecpronscanning.ui.ViewModelProviderFactory
import com.tecpron.tecpronscanning.ui.base.CustomGridLayoutManager
import com.tecpron.tecpronscanning.ui.base.ScopedFragment
import com.google.android.material.resources.TextAppearance
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.stations_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import javax.inject.Inject

class LeicaProjectsListFragment : ScopedFragment() {

    private lateinit var viewModel: LeicaProjectsListViewModel
    private lateinit var tecpronProjectSharedViewModel: TecpronProjectSharedViewModel
    private lateinit var tecpronProject: TecpronProject
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private var leicaScanningProjects: List<InternalScanningProject> = mutableListOf()
    private var stationsList: List<Station> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stations_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LeicaProjectsListViewModel::class.java)
        (activity as MainActivity?)
            ?.setActionBarTitle("Estaciones")

        tecpronProjectSharedViewModel = activity?.run {
            ViewModelProviders.of(this)[TecpronProjectSharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {

        activity?.bottom_nav?.visibility = View.VISIBLE

        tecpronProjectSharedViewModel.selected.observe(viewLifecycleOwner, androidx.lifecycle.Observer  { item ->
            tecpronProject = item
            viewModel.setTecpronProjectId(tecpronProject.id)
            (activity as MainActivity?)
                ?.setActionBarTitle(tecpronProject.name)
        })

        if(!::tecpronProject.isInitialized){
            add_station.isEnabled = false
            button_add_project.isEnabled =  false
        }

        add_station.setOnClickListener {
            val stationNumber = getNewStationNumber()
            val action = LeicaProjectsListFragmentDirections.actionLeicaProjectsListFragmentToNewStation(stationNumber,null)
            view?.findNavController()?.navigate(action)
        }

        button_add_project.setOnClickListener {
            val action = LeicaProjectsListFragmentDirections.actionLeicaProjectsListFragmentToNewLeicaScanningProjectFragment()
            view?.findNavController()?.navigate(action)
        }

        val stations = viewModel.stations.await()

        stations.observe(viewLifecycleOwner, Observer { stationResponse ->
            if (stationResponse == null) return@Observer
            stationsList = stationResponse
            initStationsRecyclerView(stationResponse.toStationItems())
        })

        val projects = viewModel.projects.await()

        projects.observe(viewLifecycleOwner, Observer { response ->
            if (response == null) return@Observer
            leicaScanningProjects = response
            initProjectsTable()
        })
    }

    private fun getNewStationNumber(): Int {
        val stations = stationsList.filter { station -> !station.cancelled }
        return stations.size + 1
    }

    private fun initProjectsTable() {
        leicaScanningProjectsTable.removeAllViews()
        val tableRowTitle = TableRow(context)
        tableRowTitle.addView(createProjectsTableTitle("Subproyecto"))
        tableRowTitle.addView(createProjectsTableTitle("Escaner"))
        tableRowTitle.addView(createProjectsTableTitle("Estaciones"))
        leicaScanningProjectsTable.addView(tableRowTitle)
        leicaScanningProjects.forEach {
            val tableRow = TableRow(context)
            tableRow.addView(createProjectsTableRow(it.name))
            tableRow.addView(createProjectsTableRow(it.scanner.name))
            tableRow.addView(createProjectsTableRow(it.stationsNumber.toString()))
            leicaScanningProjectsTable.addView(tableRow)
        }
    }

    private fun createProjectsTableTitle(text: String) : TextView {
        val textView = TextView(context)
        val layoutParams = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.MATCH_PARENT,
            1f
        )
        textView.id = View.generateViewId()
        textView.layoutParams = layoutParams
        textView.text = text
        textView.setTextAppearance(android.R.style.TextAppearance_Material_Title)
        textView.textSize = 20f
        return textView
    }

    private fun createProjectsTableRow(text: String): TextView{
        val textView = TextView(context)
        val layoutParams = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.MATCH_PARENT,
            1f
        )
        textView.id = View.generateViewId()
        textView.layoutParams = layoutParams
        textView.text = text
        textView.setTextAppearance(android.R.style.TextAppearance_Material_Body1)
        textView.textSize = 30f
        return textView
    }

    private fun List<Station>.toStationItems() : List<StationListItem> {
        return this.map {
            StationListItem(it)
        }
    }

    private fun initStationsRecyclerView(items: List<StationListItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerViewStations.apply {
            layoutManager = LinearLayoutManager(this@LeicaProjectsListFragment.context)
            adapter = groupAdapter
        }
        recyclerViewStations.requestFocus()
        recyclerViewStations.scrollToPosition(items.size - 1)

        groupAdapter.setOnItemClickListener { item, _ ->
            (item as? StationListItem)?.let {
                editStation(item.station)
            }
        }
    }

    private fun editStation(station: Station) {
        val action = LeicaProjectsListFragmentDirections.actionLeicaProjectsListFragmentToNewStation(station.stationNumber,station)
        view?.findNavController()?.navigate(action)
    }

}
