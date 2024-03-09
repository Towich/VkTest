package com.towich.vktest.ui.screen.main

import androidx.lifecycle.ViewModel
import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _listOfProducts = MutableStateFlow<List<ProductUIModel>?>(null)
    val listOfProducts: StateFlow<List<ProductUIModel>?> = _listOfProducts


}