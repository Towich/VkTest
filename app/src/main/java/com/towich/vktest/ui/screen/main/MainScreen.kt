package com.towich.vktest.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.data.source.DebugObject
import com.towich.vktest.ui.screen.main.components.EndlessGrid
import com.towich.vktest.ui.screen.main.components.ProductItemShimmerEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listOfProducts by viewModel.listOfProducts.collectAsState()

    var text by remember { mutableStateOf("") } // Query for SearchBar
    var active by remember { mutableStateOf(false) } // Active state for SearchBar
    var showSearchBar by remember { mutableStateOf(false) }

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
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    query = text,
                    onQueryChange = {
                        text = it
                    },
                    onSearch = {

                    },
                    active = active,
                    onActiveChange = {
                        active = it
                        if (!active) {
                            showSearchBar = false
                        }
                    }
                ) {
                    Text(text = "heelo")
                }
            } else {
                TopAppBar(
                    title = {
                        Text(
                            text = "Главная",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = { showSearchBar = true }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        if (uiState is MainScreenUiState.Loading && listOfProducts == null) {
            LazyVerticalGrid(
                state = rememberLazyGridState(),
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(innerPadding),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(
                    items = DebugObject.listOfProducts, // TODO
                    key = { _: Int, item: ProductUIModel ->
                        item.hashCode()
                    }
                ) { _, item ->
                    ProductItemShimmerEffect()
                }
            }
        } else {
            // observe list scrolling
            EndlessGrid(
                lazyGridState = lazyGridState,
                listOfProducts = listOfProducts,
                modifier = Modifier.padding(innerPadding),
                onReachedBottom = {
                    viewModel.loadMoreProducts()
                }
            )
        }

    }
}