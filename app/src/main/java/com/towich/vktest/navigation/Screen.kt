package com.towich.vktest.navigation

sealed class Screen(val route: String){
    data object MainScreen: Screen(route = "main_screen")
}
