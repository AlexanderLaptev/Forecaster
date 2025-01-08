package ru.trfx.apps.forecaster.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import ru.trfx.apps.forecaster.R
import java.util.Locale

class DailyWeatherAdapter(
    private val context: Context,
    var data: List<DailyWeatherUiState>,
) : RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView = view.findViewById<TextView>(R.id.text_date)
        val icon = view.findViewById<ImageView>(R.id.image_weather)
        val maxTempTextView = view.findViewById<TextView>(R.id.text_temp_max)
        val minTempTextView = view.findViewById<TextView>(R.id.text_temp_min)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_daily, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        with(holder) {
            dateTextView.text = item.date
            icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    item.weatherType.drawable
                )
            )
            maxTempTextView.text = String.format(Locale.US, "%.1f", item.maxTemp)
            minTempTextView.text = String.format(Locale.US, "%.1f", item.minTemp)
        }
    }
}
