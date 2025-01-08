package ru.trfx.apps.forecaster

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import ru.trfx.apps.forecaster.data.PreferencesConstants
import ru.trfx.apps.forecaster.ui.location.LocationActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (!prefs.contains(PreferencesConstants.LOCATION_NAME)) {
            startActivity(Intent(this, LocationActivity::class.java))
        }
    }
}
