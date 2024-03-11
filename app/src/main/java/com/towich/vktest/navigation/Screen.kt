package com.towich.vktest.navigation

sealed class Screen(val route: String) {
    data object MainScreen : Screen(route = "main_screen")
    data object ProductScreen : Screen(route = "product_screen")
}
