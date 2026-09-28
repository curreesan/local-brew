package ree.selfcode.localbrew.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.data.repository.CafeRepository
import ree.selfcode.localbrew.di.Graph

data class SearchUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val cafes: List<Cafe> = emptyList()
)

class SearchViewModel(
    private val cafeRepository: CafeRepository = Graph.cafeRepository
) : ViewModel() {

    var uiState by mutableStateOf(SearchUiState())
        private set

    fun search(lat: Double, lon: Double, query: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                val result = cafeRepository.getNearbyCafes(lat, lon, radiusMeters = 5000, name = query)
                uiState = uiState.copy(isLoading = false, cafes = result.cafes)
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, errorMessage = e.message ?: "Search failed")
            }
        }
    }
}
