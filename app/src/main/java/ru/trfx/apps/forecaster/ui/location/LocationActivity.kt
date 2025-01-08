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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.trfx.apps.forecaster.R
import ru.trfx.apps.forecaster.data.preferences.LocationPrefs
import ru.trfx.apps.forecaster.data.preferences.PreferencesRepository
import ru.trfx.apps.forecaster.ui.main.MainActivity

class LocationActivity : AppCompatActivity() {
    private val viewModel: LocationViewModel by viewModel()
    private val prefsRepo: PreferencesRepository by inject()

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

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LocationAdapter(viewModel.locationItems.value) {
            val location = viewModel.searchResults[it]
            prefsRepo.saveLocation(
                LocationPrefs(
                    location.name!!,
                    location.country!!,
                    location.latitude!!.toFloat(),
                    location.longitude!!.toFloat()
                )
            )
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            })
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
