package ru.trfx.apps.forecaster.ui.main

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.trfx.apps.forecaster.R
import ru.trfx.apps.forecaster.data.preferences.LocationPrefs
import ru.trfx.apps.forecaster.data.preferences.PreferencesRepository
import ru.trfx.apps.forecaster.ui.location.LocationActivity
import java.util.Locale
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private val prefsRepo: PreferencesRepository by inject()

    private lateinit var locationTextView: TextView
    private lateinit var currentDateTextView: TextView

    companion object {
        const val DATE_PATTERN = "EEE, MMM dd, yyyy"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locationPrefs = prefsRepo.loadLocation()
        if (locationPrefs == null) {
            startActivity(Intent(this, PreferencesRepository::class.java))
            return
        }

        setupUi()
        setupLocationText(locationPrefs)
        setupDateText()
        setupChangeLocationButton()
        setupRefreshButton()
        setupAboutButton()
        setupViewmodel()

        viewModel.refresh()
    }

    private fun setupUi() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupAboutButton() {
        findViewById<ImageButton>(R.id.button_about).setOnClickListener {
            AlertDialog
                .Builder(this)
                .setTitle("About Forecaster")
                .setMessage("A very simple weather application for Android.")
                .setCancelable(true)
                .setPositiveButton("OK") { _, _ -> }
                .show()
        }
    }

    private fun setupRefreshButton() {
        findViewById<ImageButton>(R.id.button_refresh).setOnClickListener {
            viewModel.refresh()
        }
    }

    private fun setupChangeLocationButton() {
        findViewById<ImageButton>(R.id.button_change_location).setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }
    }

    private fun setupViewmodel() {
        val temperature = findViewById<TextView>(R.id.text_current_temp)
        val humidity = findViewById<TextView>(R.id.text_humidity_value)
        val windSpeed = findViewById<TextView>(R.id.text_wind_value)
        val pressure = findViewById<TextView>(R.id.text_pressure_value)
        val precipitationChance = findViewById<TextView>(R.id.text_chance_value)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    temperature.text = String.format(Locale.US, "%.1f", it.temperature)
                    humidity.text = "${it.humidity}%"
                    windSpeed.text = String.format(Locale.US, "%.1f", it.windSpeed)
                    pressure.text = String.format(Locale.US, "%.1f", it.pressure)
                    precipitationChance.text = "${it.precipitationChance}%"

                    Toast.makeText(
                        this@MainActivity,
                        "Weather updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupDateText() {
        currentDateTextView = findViewById(R.id.text_current_date)
        val formatter = SimpleDateFormat(DATE_PATTERN, Locale.US)
        val date = Calendar.getInstance().time
        currentDateTextView.text = formatter.format(date)
    }

    private fun setupLocationText(prefs: LocationPrefs) {
        locationTextView = findViewById(R.id.text_location)
        locationTextView.text = "${prefs.name},\n${prefs.country}"
    }
}
