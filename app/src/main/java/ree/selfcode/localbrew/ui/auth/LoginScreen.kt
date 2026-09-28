package ree.selfcode.localbrew.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoggedIn: () -> Unit,
    onNavigateToSignup: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onLoggedIn()
    }

    AuthForm(
        title = "Log In",
        buttonText = "Log In",
        footerText = "Don't have an account? Sign up",
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onSubmit = { email, password -> viewModel.login(email, password) },
        onFooterClick = onNavigateToSignup
    )
}
