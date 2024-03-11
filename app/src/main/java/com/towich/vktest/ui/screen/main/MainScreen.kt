package com.towich.vktest.ui.screen.main

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.towich.vktest.R
import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.navigation.Screen
import com.towich.vktest.ui.screen.main.components.CategoriesLazyRow
import com.towich.vktest.ui.screen.main.components.EndlessGrid
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    context: Context,
    viewModel: MainViewModel = hiltViewModel()
) {
    val productsUiState by viewModel.productsUiState.collectAsState()
    val categoriesUiState by viewModel.categoriesUiState.collectAsState()
    val searchUiState by viewModel.searchUiState.collectAsState()

    val listOfProducts by viewModel.listOfProducts.collectAsState()
    val listOfSearchedProducts by viewModel.listOfSearchedProducts.collectAsState()
    val listOfCategories by viewModel.listOfCategories.collectAsState()
    val currentCategory by viewModel.currentCategory.collectAsState()

    var searchBarText by rememberSaveable { mutableStateOf("") }
    var searchBarQuery by rememberSaveable { mutableStateOf("") }
    var searchBarActive by rememberSaveable { mutableStateOf(false) }
    var showSearchBar by rememberSaveable { mutableStateOf(false) }
    var selectedChipIndex by rememberSaveable { mutableStateOf(0) }

    val scope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridState()

    when (productsUiState) {
        is ProductsUiState.Success<*> -> {
            viewModel.changeProductsUiState(ProductsUiState.Initial)
        }

        is ProductsUiState.Error -> {
            Toast.makeText(
                context,
                (productsUiState as ProductsUiState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            viewModel.changeProductsUiState(ProductsUiState.Initial)
        }

        else -> {}
    }
    when (categoriesUiState) {
        is CategoriesUiState.Success<*> -> {
            viewModel.changeCategoriesUiState(CategoriesUiState.Initial)
        }

        is CategoriesUiState.Error -> {
            Toast.makeText(
                context,
                (categoriesUiState as CategoriesUiState.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            viewModel.changeCategoriesUiState(CategoriesUiState.Initial)
        }

        else -> {}
    }
    when (searchUiState) {
        is SearchUiState.Success<*> -> {
            viewModel.changeSearchUiState(SearchUiState.Initial)
        }

        is SearchUiState.Error -> {
            if ((searchUiState as SearchUiState.Error).message == "Empty list!") {
                Toast.makeText(
                    context,
                    "${stringResource(id = R.string.empty_search)} " +
                            "'${searchBarQuery}'!",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(
                    context,
                    (searchUiState as SearchUiState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            viewModel.changeSearchUiState(SearchUiState.Initial)
        }

        else -> {}
    }

    Scaffold(
        topBar = {
            if (showSearchBar) {
                searchBarActive = true
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    query = searchBarText,
                    onQueryChange = {
                        searchBarText = it
                    },
                    onSearch = { query ->
                        searchBarQuery = query
                        viewModel.searchProducts(query)
                    },
                    active = searchBarActive,
                    onActiveChange = {
                        searchBarActive = it
                        if (!searchBarActive) {
                            viewModel.clearListOfSearchedProducts()
                            selectedChipIndex = 0
                            showSearchBar = false
                            scope.launch {
                                lazyGridState.scrollToItem(0)
                            }
                        }
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.search_bar_placeholder))
                    },
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    EndlessGrid(
                        lazyGridState = rememberLazyGridState(),
                        listOfProducts = listOfSearchedProducts,
                        isLoading = searchUiState is SearchUiState.Loading,
                        onProductClicked = { product: ProductUIModel ->
                            viewModel.setCurrentProduct(product)
                            navController.navigate(Screen.ProductScreen.route)
                        }
                    )
                }
            } else {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.mainscreen_top_bar_title),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            showSearchBar = true
                            searchBarText = ""
                            viewModel.clearListOfSearchedProducts()
                            viewModel.clearCurrentCategory()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = stringResource(id = R.string.button_search)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CategoriesLazyRow(
                listOfCategories = listOfCategories ?: listOf(),
                isLoading = categoriesUiState is CategoriesUiState.Loading && listOfCategories == null,
                selectedChipIndex = selectedChipIndex,
                modifier = Modifier.padding(top = 10.dp),
                onSelectChip = { category, index ->
                    selectedChipIndex = index

                    if (selectedChipIndex == 0) {
                        viewModel.clearListOfSearchedProducts()
                        viewModel.clearCurrentCategory()
                    } else {
                        viewModel.searchProductsByCategory(category = category)
                    }

                    scope.launch {
                        lazyGridState.scrollToItem(index = 0)
                    }
                }
            )

            HorizontalDivider(modifier = Modifier.padding(top = 10.dp))

            EndlessGrid(
                lazyGridState = lazyGridState,
                listOfProducts = if (currentCategory == null) listOfProducts else listOfSearchedProducts,
                isLoading = (productsUiState is ProductsUiState.Loading && listOfProducts == null)
                        || (categoriesUiState is CategoriesUiState.Loading),
                onReachedBottom = {
                    viewModel.loadMoreProducts(category = currentCategory)
                },
                onProductClicked = { product: ProductUIModel ->
                    viewModel.setCurrentProduct(product)
                    navController.navigate(Screen.ProductScreen.route)
                }
            )
        }
    }
}