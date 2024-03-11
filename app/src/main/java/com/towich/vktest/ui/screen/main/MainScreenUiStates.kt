package com.towich.vktest.ui.screen.main


sealed interface ProductsUiState {
    data object Initial : ProductsUiState
    data object Loading : ProductsUiState
    data class Success<out T>(val data: T) : ProductsUiState
    data class Error(val message: String) : ProductsUiState
}

sealed interface CategoriesUiState {
    data object Initial : CategoriesUiState
    data object Loading : CategoriesUiState
    data class Success<out T>(val data: T) : CategoriesUiState
    data class Error(val message: String) : CategoriesUiState
}

sealed interface SearchUiState {
    data object Initial : SearchUiState
    data object Loading : SearchUiState
    data class Success<out T>(val data: T) : SearchUiState
    data class Error(val message: String) : SearchUiState
}