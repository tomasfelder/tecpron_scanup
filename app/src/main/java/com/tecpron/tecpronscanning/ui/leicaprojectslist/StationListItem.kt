package com.tecpron.tecpronscanning.ui.leicaprojectslist

import android.graphics.Color
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.amplifyframework.datastore.generated.model.Station
import com.tecpron.tecpronscanning.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_list_station.*

class StationListItem(
    val station: Station
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_station_number.text = "Est. ${station.stationNumber}"
            textView_project_name.text = station.leicaScanningProject.name
            if(station.cancelled)
                card.setCardBackgroundColor(Color.RED)
            if(station.notes.size > 0){
                val textViewNotesSize = TextView(iconsLayout.context)
                val layoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START)
                textViewNotesSize.setPadding(10)
                textViewNotesSize.layoutParams = layoutParams
                textViewNotesSize.text = station.notes.size.toString()
                textViewNotesSize.id = View.generateViewId()
                iconsLayout.addView(textViewNotesSize)
                val imageView = ImageView(iconsLayout.context)
                val layoutParams2 = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                layoutParams2.addRule(RelativeLayout.RIGHT_OF,textViewNotesSize.id)
                imageView.setPadding(10)
                imageView.layoutParams = layoutParams2
                imageView.setImageResource(R.drawable.ic_event_note_black_24dp)
                iconsLayout.addView(imageView)

            }


        }
    }

    override fun getLayout() = R.layout.item_list_station

}