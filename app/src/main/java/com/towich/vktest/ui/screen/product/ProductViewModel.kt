package com.towich.vktest.ui.screen.product

import androidx.lifecycle.ViewModel
import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    fun getCurrentProduct(): ProductUIModel? {
        return repository.getCurrentProduct()
    }
}