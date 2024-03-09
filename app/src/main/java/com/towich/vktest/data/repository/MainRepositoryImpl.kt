package com.towich.vktest.data.repository

import android.app.Application
import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.network.ApiService
import com.towich.vktest.util.ApiResult

class MainRepositoryImpl(
    private val appContext: Application,
    private val apiService: ApiService
): MainRepository {
    override suspend fun getProducts(): ApiResult<List<ProductUIModel>> {
        return apiService.getProducts()
    }

    override suspend fun getProducts(skip: Int): ApiResult<List<ProductUIModel>> {
        return apiService.getProducts(skip = skip)
    }

    override suspend fun getProducts(query: String): ApiResult<List<ProductUIModel>> {
        return apiService.getProducts(query = query)
    }


}