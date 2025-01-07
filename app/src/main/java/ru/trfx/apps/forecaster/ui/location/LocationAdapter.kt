package ru.trfx.apps.forecaster.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.trfx.apps.forecaster.R

class LocationAdapter(
    var data: List<LocationItem>,
) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView = view.findViewById<TextView>(R.id.text_name)
        val detailsView = view.findViewById<TextView>(R.id.text_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        with(holder) {
            nameView.text = item.name
            detailsView.text = item.details
        }
    }
}
