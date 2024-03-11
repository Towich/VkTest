package com.towich.vktest.network

import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.util.ApiResult


interface ApiService {
    suspend fun getCategories(): ApiResult<List<String>>
    suspend fun getProducts(): ApiResult<List<ProductUIModel>>
    suspend fun getProducts(skip: Int, category: String? = null): ApiResult<List<ProductUIModel>>
    suspend fun getProductsByQuery(query: String): ApiResult<List<ProductUIModel>>
    suspend fun getProductsByCategory(category: String): ApiResult<List<ProductUIModel>>
}