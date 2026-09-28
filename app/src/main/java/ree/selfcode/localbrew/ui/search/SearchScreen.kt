package ree.selfcode.localbrew.ui.search

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.ui.components.CafeList

@Composable
fun SearchScreen(location: Location, onCafeClick: (Cafe) -> Unit) {
    var query by remember { mutableStateOf("") }
    val viewModel: SearchViewModel = viewModel()
    val searchState = viewModel.uiState

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search cafés by name") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { viewModel.search(location.latitude, location.longitude, query) },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Search")
        }
        CafeList(searchState.isLoading, searchState.errorMessage, searchState.cafes, onCafeClick)
    }
}
