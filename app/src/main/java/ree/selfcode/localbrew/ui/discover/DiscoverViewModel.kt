package ree.selfcode.localbrew.ui.discover

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.data.repository.CafeRepository
import ree.selfcode.localbrew.di.Graph

data class DiscoverUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val cafes: List<Cafe> = emptyList(),
    val isShowingCachedData: Boolean = false
)

class DiscoverViewModel(
    private val cafeRepository: CafeRepository = Graph.cafeRepository
) : ViewModel() {

    var uiState by mutableStateOf(DiscoverUiState())
        private set

    fun loadNearbyCafes(lat: Double, lon: Double) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                val result = cafeRepository.getNearbyCafes(lat, lon, radiusMeters = 2000)
                uiState = uiState.copy(
                    isLoading = false,
                    cafes = result.cafes,
                    isShowingCachedData = result.isFromCache
                )
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, errorMessage = e.message ?: "Couldn't load nearby cafés")
            }
        }
    }
}
