package com.towich.vktest.network

import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.util.ApiResult


interface ApiService {
    suspend fun getProducts(): ApiResult<List<ProductUIModel>>
    suspend fun getProducts(skip: Int): ApiResult<List<ProductUIModel>>
}