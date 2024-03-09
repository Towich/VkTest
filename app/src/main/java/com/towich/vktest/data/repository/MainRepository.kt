package com.towich.vktest.data.repository

import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.util.ApiResult

interface MainRepository {
    suspend fun getProducts(): ApiResult<List<ProductUIModel>>
    suspend fun getProducts(skip: Int): ApiResult<List<ProductUIModel>>
    suspend fun getProducts(query: String): ApiResult<List<ProductUIModel>>
}