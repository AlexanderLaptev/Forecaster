package ru.trfx.apps.forecaster.ui.hourly

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import ru.trfx.apps.forecaster.R
import ru.trfx.apps.forecaster.util.hPaToMmHg
import java.util.Locale
import kotlin.math.roundToInt

class HourlyDetailsAdapter(
    private val context: Context,
    var data: List<HourlyDetailsItem>,
) : RecyclerView.Adapter<HourlyDetailsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time = view.findViewById<TextView>(R.id.text_time)
        val icon = view.findViewById<ImageView>(R.id.image_weather)
        val temperature = view.findViewById<TextView>(R.id.text_temp)
        val humidity = view.findViewById<TextView>(R.id.text_humidity)
        val windSpeed = view.findViewById<TextView>(R.id.text_wind_speed)
        val pressure = view.findViewById<TextView>(R.id.text_pressure)
        val chance = view.findViewById<TextView>(R.id.text_chance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_hourly, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        with(holder) {
            time.text = item.time
            icon.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    item.weatherType.drawable
                )
            )
            temperature.text = String.format(Locale.US, "%.1f", item.temperature)
            humidity.text = "${item.humidity}%"
            windSpeed.text = String.format(Locale.US, "%.1f", item.windSpeed)
            windSpeed.text = String.format(Locale.US, "%.1f", item.windSpeed)
            pressure.text = hPaToMmHg(item.pressure).roundToInt().toString()
            chance.text = "${item.precipitationChance}%"
        }
    }
}
