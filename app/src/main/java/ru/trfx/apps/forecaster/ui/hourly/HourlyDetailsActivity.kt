package ru.trfx.apps.forecaster.ui.hourly

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.trfx.apps.forecaster.R

class HourlyDetailsActivity : AppCompatActivity() {
    private val viewModel: HourlyDetailsViewModel by viewModel()

    private lateinit var adapter: HourlyDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        setupRecyclerView()
        setupViewmodel()

        viewModel.refresh()
    }

    private fun setupUi() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_hourly_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = HourlyDetailsAdapter(this, emptyList())
        recycler.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupViewmodel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hourlyDetails.collect {
                    adapter.data = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
