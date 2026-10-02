package ree.selfcode.localbrew.ui.detail

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

data class DetailUiState(
    val isLoading: Boolean = true,
    val isFavorited: Boolean = false,
    val isTogglingFavorite: Boolean = false,
    val errorMessage: String? = null
)

class DetailViewModel(
    private val firestoreRepository: FirestoreRepository = Graph.firestoreRepository,
    private val authRepository: AuthRepository = Graph.authRepository
) : ViewModel() {

    var uiState by mutableStateOf(DetailUiState())
        private set

    fun checkFavorited(cafeId: String) {
        val uid = authRepository.currentUserId
        if (uid == null) {
            uiState = uiState.copy(isLoading = false, errorMessage = "Not logged in")
            return
        }
        viewModelScope.launch {
            val isFav = firestoreRepository.isFavorited(uid, cafeId)
            uiState = uiState.copy(isLoading = false, isFavorited = isFav)
        }
    }

    fun toggleFavorite(cafe: Cafe) {
        val uid = authRepository.currentUserId
        if (uid == null) {
            uiState = uiState.copy(errorMessage = "Not logged in")
            return
        }
        viewModelScope.launch {
            uiState = uiState.copy(isTogglingFavorite = true)
            try {
                if (uiState.isFavorited) {
                    firestoreRepository.removeFavorite(uid, cafe.id)
                    uiState = uiState.copy(isFavorited = false)
                } else {
                    firestoreRepository.addFavorite(uid, cafe)
                    uiState = uiState.copy(isFavorited = true)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = e.message ?: "Something went wrong")
            } finally {
                uiState = uiState.copy(isTogglingFavorite = false)
            }
        }
    }
}
