package ree.selfcode.localbrew.ui.favorites

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.ui.components.CafeList

@Composable
fun FavoritesScreen(location: Location, onCafeClick: (Cafe) -> Unit) {
    val viewModel: FavoritesViewModel = viewModel()
    val favoritesState = viewModel.uiState

    LaunchedEffect(location) {
        viewModel.observe(location)
    }

    CafeList(favoritesState.isLoading, favoritesState.errorMessage, favoritesState.cafes, onCafeClick)
}
