package com.tecpron.tecpronscanning.ui.leicaprojectslist

import com.tecpron.tecpronscanning.R
import com.tecpron.tecpronscanning.data.model.InternalScanningProject
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_list_project.*

class LeicaScanningProjectsListItem(
    val project: InternalScanningProject
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_project.text = project.name
            textView_scanner.text = project.scanner.name
            textView_stations_quantity.text = "${project.stationsNumber}"
        }
    }

    override fun getLayout() = R.layout.item_list_project

}