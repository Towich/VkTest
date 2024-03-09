package com.towich.vktest.ui.screen.main


sealed interface MainScreenUiState{
    data object Initial: MainScreenUiState
    data object Loading: MainScreenUiState
    data class Success<out T>(val data: T): MainScreenUiState
    data class Error(val message: String): MainScreenUiState
}