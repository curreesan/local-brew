package ree.selfcode.localbrew.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.di.Graph
import ree.selfcode.localbrew.ui.auth.LoginScreen
import ree.selfcode.localbrew.ui.auth.SignupScreen
import ree.selfcode.localbrew.ui.detail.DetailScreen
import ree.selfcode.localbrew.ui.main.MainScreen
import ree.selfcode.localbrew.ui.profile.ProfileScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val startDestination = if (Graph.authRepository.currentUserId != null) {
        Screen.Main.route
    } else {
        Screen.Login.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoggedIn = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = { navController.navigate(Screen.Signup.route) }
            )
        }
        composable(Screen.Signup.route) {
            SignupScreen(
                onSignedUp = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Main.route) {
            MainScreen(
                onCafeClick = { cafe ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("cafe", cafe)
                    navController.navigate(Screen.Detail.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onLogout = {
                    Graph.authRepository.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val cafe = remember(backStackEntry) {
                navController.previousBackStackEntry?.savedStateHandle?.get<Cafe>("cafe")
            }
            if (cafe != null) {
                DetailScreen(cafe = cafe, onBack = { navController.popBackStack() })
            } else {
                Text("Something went wrong")
            }
        }
        composable(Screen.Profile.route) {
            ProfileScreen(onBack = { navController.popBackStack() })
        }
    }
}
