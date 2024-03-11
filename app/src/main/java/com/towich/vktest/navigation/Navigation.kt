package com.towich.vktest.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.data.source.Constants
import com.towich.vktest.ui.screen.main.MainScreen
import com.towich.vktest.ui.screen.product.ProductScreen

@Composable
fun Navigation(
    navController: NavHostController,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route){
            MainScreen(
                navController = navController,
                context = context
            )
        }
        composable(route = Screen.ProductScreen.route) {
            ProductScreen(navController = navController)
        }
    }
}