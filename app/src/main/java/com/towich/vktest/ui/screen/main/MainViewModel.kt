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
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _productsUiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Initial)
    val productsUiState: StateFlow<ProductsUiState> = _productsUiState

    private val _categoriesUiState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Initial)
    val categoriesUiState: StateFlow<CategoriesUiState> = _categoriesUiState

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchUiState: StateFlow<SearchUiState> = _searchUiState

    private val _listOfProducts = MutableStateFlow<MutableList<ProductUIModel>?>(null)
    val listOfProducts: StateFlow<List<ProductUIModel>?> = _listOfProducts

    private val _listOfSearchedProducts = MutableStateFlow<MutableList<ProductUIModel>?>(null)
    val listOfSearchedProducts: StateFlow<List<ProductUIModel>?> = _listOfSearchedProducts

    private val _listOfCategories = MutableStateFlow<List<String>?>(null)
    val listOfCategories: StateFlow<List<String>?> = _listOfCategories

    private val _currentCategory = MutableStateFlow<String?>(null)
    val currentCategory: StateFlow<String?> = _currentCategory

    private var currentPage = 0

    init {
        getAllCategories()
        getAllProducts()
    }

    fun changeProductsUiState(newState: ProductsUiState) {
        _productsUiState.value = newState
    }

    fun changeCategoriesUiState(newState: CategoriesUiState) {
        _categoriesUiState.value = newState
    }

    fun changeSearchUiState(newState: SearchUiState) {
        _searchUiState.value = newState
    }


    private fun getAllCategories() {
        viewModelScope.launch {
            _categoriesUiState.value = CategoriesUiState.Loading

            when (val result = repository.getCategories()) {
                is ApiResult.Success -> {
                    _categoriesUiState.value = CategoriesUiState.Success(result.data)
                    _listOfCategories.value = result.data
                }

                is ApiResult.Error -> {
                    _categoriesUiState.value = CategoriesUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _productsUiState.value = ProductsUiState.Loading

            when (val result = repository.getProducts()) {
                is ApiResult.Success -> {
                    _productsUiState.value = ProductsUiState.Success(result.data)
                    _listOfProducts.value = result.data.toMutableList()
                    currentPage++
                }

                is ApiResult.Error -> {
                    _productsUiState.value = ProductsUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    fun loadMoreProducts(category: String? = null) {
        viewModelScope.launch {
            _productsUiState.value = ProductsUiState.Loading

            when (val result = repository.getProducts(
                skip = currentPage * Constants.LIMIT_PRODUCTS,
                category = category
            )) {
                is ApiResult.Success -> {
                    _productsUiState.value = ProductsUiState.Success(result.data)

                    if(category != null){
                        _listOfSearchedProducts.value?.addAll(result.data)
                    }
                    else{
                        _listOfProducts.value?.addAll(result.data)
                    }

                    if(result.data.isNotEmpty()){
                        currentPage++
                    }
                }

                is ApiResult.Error -> {
                    _productsUiState.value = ProductsUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _searchUiState.value = SearchUiState.Loading

            when (val result = repository.getProductsByQuery(query = query)) {
                is ApiResult.Success -> {
                    _searchUiState.value = SearchUiState.Success(result.data)
                    _listOfSearchedProducts.value = result.data.toMutableList()
                }

                is ApiResult.Error -> {
                    _searchUiState.value = SearchUiState.Error(result.error)
                    _listOfSearchedProducts.value = mutableListOf()
                }

                else -> {

                }
            }
        }
    }

    fun searchProductsByCategory(category: String) {
        currentPage = 0
        viewModelScope.launch {
            _categoriesUiState.value = CategoriesUiState.Loading

            when (val result = repository.getProductsByCategory(category = category)) {
                is ApiResult.Success -> {
                    _categoriesUiState.value = CategoriesUiState.Success(result.data)
                    _currentCategory.value = category
                    _listOfSearchedProducts.value = result.data.toMutableList()

                    if(result.data.isNotEmpty()){
                        currentPage++
                    }

                }

                is ApiResult.Error -> {
                    _categoriesUiState.value = CategoriesUiState.Error(result.error)
                }

                else -> {

                }
            }
        }
    }

    fun setCurrentProduct(newProduct: ProductUIModel) {
        repository.setCurrentProduct(newProduct)
    }

    fun clearListOfSearchedProducts() {
        _listOfSearchedProducts.value = null
    }

    fun clearCurrentCategory() {
        _currentCategory.value = null
    }
}