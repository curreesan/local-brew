package ree.selfcode.localbrew.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignupScreen(
    onSignedUp: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onSignedUp()
    }

    AuthForm(
        title = "Sign Up",
        buttonText = "Sign Up",
        footerText = "Already have an account? Log in",
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onSubmit = { email, password -> viewModel.signUp(email, password) },
        onFooterClick = onNavigateToLogin
    )
}
