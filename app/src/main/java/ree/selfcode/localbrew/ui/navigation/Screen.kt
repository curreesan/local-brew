package ree.selfcode.localbrew.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Main : Screen("main")
    object Detail : Screen("detail")
    object Profile : Screen("profile")
}
