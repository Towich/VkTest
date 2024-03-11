package com.towich.vktest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.towich.vktest.navigation.Navigation
import com.towich.vktest.ui.theme.VkTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            VkTestTheme {
                Navigation(
                    navController = navController,
                    context = applicationContext
                )
            }
        }
    }
}