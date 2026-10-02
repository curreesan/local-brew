package ree.selfcode.localbrew.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ree.selfcode.localbrew.data.firebase.AuthRepository
import ree.selfcode.localbrew.data.firebase.FirestoreRepository
import ree.selfcode.localbrew.data.model.Profile
import ree.selfcode.localbrew.di.Graph

data class ProfileUiState(
    val isLoading: Boolean = true,
    val username: String = "",
    val description: String = "",
    val errorMessage: String? = null
)

class ProfileViewModel(
    private val firestoreRepository: FirestoreRepository = Graph.firestoreRepository,
    private val authRepository: AuthRepository = Graph.authRepository
) : ViewModel() {

    var uiState by mutableStateOf(ProfileUiState())
        private set

    fun load() {
        val uid = authRepository.currentUserId
        if (uid == null) {
            uiState = uiState.copy(isLoading = false, errorMessage = "Not logged in")
            return
        }
        viewModelScope.launch {
            val profile = firestoreRepository.getProfile(uid)
            uiState = uiState.copy(isLoading = false, username = profile.username, description = profile.description)
        }
    }

    fun save(username: String, description: String) {
        val uid = authRepository.currentUserId
        if (uid == null) {
            uiState = uiState.copy(errorMessage = "Not logged in")
            return
        }
        viewModelScope.launch {
            try {
                firestoreRepository.saveProfile(uid, Profile(username, description))
                uiState = uiState.copy(username = username, description = description)
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = e.message ?: "Couldn't save profile")
            }
        }
    }
}
