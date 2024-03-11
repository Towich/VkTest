package com.towich.vktest.data.repository

import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.util.ApiResult

interface MainRepository {
    suspend fun getCategories(): ApiResult<List<String>>
    suspend fun getProducts(): ApiResult<List<ProductUIModel>>
    suspend fun getProducts(skip: Int, category: String?): ApiResult<List<ProductUIModel>>
    suspend fun getProductsByQuery(query: String): ApiResult<List<ProductUIModel>>
    suspend fun getProductsByCategory(category: String): ApiResult<List<ProductUIModel>>

    fun getCurrentProduct(): ProductUIModel?
    fun setCurrentProduct(newProduct: ProductUIModel)
}