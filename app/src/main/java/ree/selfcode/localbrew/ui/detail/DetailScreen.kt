package ree.selfcode.localbrew.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ree.selfcode.localbrew.R
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.ui.discover.formatDistance
import ree.selfcode.localbrew.ui.theme.IBMPlexMonoFamily

@Composable
fun DetailScreen(cafe: Cafe, onBack: () -> Unit) {
    val viewModel: DetailViewModel = viewModel()
    val uiState = viewModel.uiState

    LaunchedEffect(cafe.id) {
        viewModel.checkFavorited(cafe.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("← Back")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = cafe.name.firstOrNull()?.uppercase() ?: "?",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(cafe.name, style = MaterialTheme.typography.headlineMedium)
        Text(
            cafe.address,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            formatDistance(cafe.distanceMeters),
            style = MaterialTheme.typography.labelLarge.copy(fontFamily = IBMPlexMonoFamily),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (!uiState.isLoading) {
            Button(
                onClick = { viewModel.toggleFavorite(cafe) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(
                        id = if (uiState.isFavorited) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_unfilled
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (uiState.isFavorited) "Remove from Favorites" else "Save to Favorites")
            }
        }
        uiState.errorMessage?.let { Text(it) }
    }
}
