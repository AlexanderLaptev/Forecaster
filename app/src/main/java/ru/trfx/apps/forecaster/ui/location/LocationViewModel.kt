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
import ru.trfx.apps.forecaster.data.location.LocationRepository
import ru.trfx.apps.forecaster.data.location.LocationSearchResult

@OptIn(FlowPreview::class)
class LocationViewModel(
    private val repository: LocationRepository,
) : ViewModel() {
    companion object {
        private const val DEBOUNCE_TIME_MS = 500L
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _locationItems = MutableStateFlow<List<LocationItem>>(emptyList())
    val locationItems = _locationItems.asStateFlow()

    var searchResults: List<LocationSearchResult> = emptyList()

    init {
        viewModelScope.launch {
            // TODO: tweak debouncing
            _searchQuery.debounce(DEBOUNCE_TIME_MS).collectLatest { query ->
                if (query.isBlank()) return@collectLatest
                launch(Dispatchers.IO) {
                    searchResults = repository.searchLocations(query, "en").results
                    val items = searchResults.map {
                        LocationItem(
                            it.name ?: "N/A",
                            it.admin1 ?: "N/A",
                            it.country ?: "N/A",
                        )
                    }
                    _locationItems.update { items }
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
        if (query.isBlank()) {
            _locationItems.update { emptyList() }
        }
    }
}
