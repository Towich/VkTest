package com.towich.vktest.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.data.repository.MainRepository
import com.towich.vktest.data.source.Constants
import com.towich.vktest.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<MainScreenUiState>(MainScreenUiState.Initial)
    val uiState: StateFlow<MainScreenUiState> = _uiState

    private val _listOfProducts = MutableStateFlow<MutableList<ProductUIModel>?>(null)
    val listOfProducts: StateFlow<List<ProductUIModel>?> = _listOfProducts

    var currentPage = 0

    init {
        performGetListOfProducts()
    }

    fun changeUiState(newState: MainScreenUiState){
        _uiState.value = newState
    }

    private fun performGetListOfProducts() {
        viewModelScope.launch {
            _uiState.value = MainScreenUiState.Loading

            when (val result = repository.getProducts()) {
                is ApiResult.Success -> {
                    _uiState.value = MainScreenUiState.Success(result.data)
                    _listOfProducts.value = result.data.toMutableList()
                    currentPage++
                }

                is ApiResult.Error -> {
                    _uiState.value = MainScreenUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    fun loadMoreProducts(){
        viewModelScope.launch {
            _uiState.value = MainScreenUiState.Loading

            when (val result = repository.getProducts(currentPage * Constants.LIMIT_PRODUCTS)) {
                is ApiResult.Success -> {
                    _uiState.value = MainScreenUiState.Success(result.data)
                    _listOfProducts.value?.addAll(result.data)
                    currentPage++
                }

                is ApiResult.Error -> {
                    _uiState.value = MainScreenUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }
}