package ree.selfcode.localbrew.ui.favorites

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ree.selfcode.localbrew.data.firebase.AuthRepository
import ree.selfcode.localbrew.data.firebase.FirestoreRepository
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.di.Graph

data class FavoritesUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val cafes: List<Cafe> = emptyList()
)

class FavoritesViewModel(
    private val firestoreRepository: FirestoreRepository = Graph.firestoreRepository,
    private val authRepository: AuthRepository = Graph.authRepository
) : ViewModel() {

    var uiState by mutableStateOf(FavoritesUiState())
        private set

    fun observe(location: Location) {
        val uid = authRepository.currentUserId
        if (uid != null) {
            viewModelScope.launch {
                firestoreRepository.observeFavorites(uid).collect { cafes ->
                    val withDistance = cafes.map { cafe ->
                        val results = FloatArray(1)
                        Location.distanceBetween(location.latitude, location.longitude, cafe.lat, cafe.lon, results)
                        cafe.copy(distanceMeters = results[0])
                    }
                    uiState = uiState.copy(isLoading = false, cafes = withDistance)
                }
            }
        } else {
            uiState = uiState.copy(isLoading = false, errorMessage = "Not logged in")
        }
    }
}
