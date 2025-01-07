package ru.trfx.apps.forecaster.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.trfx.apps.forecaster.data.location.MockLocationRepo

@OptIn(FlowPreview::class)
class LocationViewModel : ViewModel() {
    companion object {
        private const val DEBOUNCE_TIME_MS = 750L
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _locationItems = MutableStateFlow<List<LocationItem>>(emptyList())
    val locationItems = _locationItems.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery.debounce(DEBOUNCE_TIME_MS).collectLatest { query ->
                launch(Dispatchers.IO) {
                    val results = MockLocationRepo.searchLocations(query, "en").results
                    val items = results.map { LocationItem(it.name!!, it.admin1!!, it.country!!) }
                    _locationItems.update { items }
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }
}
