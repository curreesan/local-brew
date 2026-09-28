package ree.selfcode.localbrew.ui.discover

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.ui.components.CafeList

fun formatDistance(meters: Float): String {
    return if (meters < 1000) {
        "${meters.toInt()}m away"
    } else {
        "%.1f".format(meters / 1000) + "km away"
    }
}

@Composable
fun DiscoverScreen(location: Location, onCafeClick: (Cafe) -> Unit) {
    val viewModel: DiscoverViewModel = viewModel()
    val discoverState = viewModel.uiState

    LaunchedEffect(location) {
        viewModel.loadNearbyCafes(location.latitude, location.longitude)
    }

    Column {
        if (discoverState.isShowingCachedData) {
            Text(
                text = "Showing saved results — you're offline",
                modifier = Modifier.padding(8.dp)
            )
        }
        CafeList(discoverState.isLoading, discoverState.errorMessage, discoverState.cafes, onCafeClick)
    }
}
