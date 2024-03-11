package com.towich.vktest.ui.screen.main

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
import androidx.compose.runtime.remember
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
import com.towich.vktest.ui.screen.main.components.EndlessGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listOfProducts by viewModel.listOfProducts.collectAsState()
    val listOfSearchedProducts by viewModel.listOfSearchedProducts.collectAsState()

    var searchBarText by rememberSaveable { mutableStateOf("") }
    var searchBarActive by rememberSaveable { mutableStateOf(false) }
    var showSearchBar by rememberSaveable { mutableStateOf(false) }

    when (uiState) {
        is MainScreenUiState.Success<*> -> {
            viewModel.changeUiState(MainScreenUiState.Initial)
        }

        is MainScreenUiState.Error -> {
            // TODO
        }

        else -> {}
    }

    val lazyGridState = rememberLazyGridState()

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
                        viewModel.searchProducts(query)
                    },
                    active = searchBarActive,
                    onActiveChange = {
                        searchBarActive = it
                        if (!searchBarActive) {
                            showSearchBar = false
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
                        isLoading = uiState is MainScreenUiState.Loading,
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
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(innerPadding)
        )

        EndlessGrid(
            lazyGridState = lazyGridState,
            listOfProducts = listOfProducts,
            isLoading = uiState is MainScreenUiState.Loading && listOfProducts == null,
            modifier = Modifier
                .padding(innerPadding)
                .padding(1.dp),
            onReachedBottom = {
                viewModel.loadMoreProducts()
            },
            onProductClicked = { product: ProductUIModel ->
                viewModel.setCurrentProduct(product)
                navController.navigate(Screen.ProductScreen.route)
            }
        )
    }
}