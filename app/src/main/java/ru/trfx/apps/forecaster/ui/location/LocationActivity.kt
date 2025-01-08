package ru.trfx.apps.forecaster.ui.location

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.trfx.apps.forecaster.MainActivity
import ru.trfx.apps.forecaster.R
import ru.trfx.apps.forecaster.data.PreferencesConstants

class LocationActivity : AppCompatActivity() {
    private val viewModel: LocationViewModel by viewModel()

    private lateinit var searchView: SearchView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LocationAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_location)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchView = findViewById(R.id.search_view)
        with(searchView) {
            setIconifiedByDefault(false)
            isIconified = false
            queryHint = "Type to search locations"
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null) return false
                    viewModel.updateSearchQuery(newText)
                    return true
                }
            })
        }

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LocationAdapter(viewModel.locationItems.value) {
            val location = viewModel.searchResults[it]
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            with(prefs.edit()) {
                putFloat(PreferencesConstants.LOCATION_LATITUDE, location.latitude!!.toFloat())
                putFloat(PreferencesConstants.LOCATION_LONGITUDE, location.longitude!!.toFloat())
                putString(PreferencesConstants.LOCATION_NAME, location.name!!)
                putString(PreferencesConstants.LOCATION_COUNTRY, location.country!!)
                apply()
            }
            startActivity(Intent(this, MainActivity::class.java))
        }
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locationItems.collect {
                    adapter.data = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
