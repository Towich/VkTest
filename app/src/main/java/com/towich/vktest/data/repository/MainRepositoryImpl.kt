package com.towich.vktest.data.repository

import android.app.Application
import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.data.source.SessionStorage
import com.towich.vktest.network.ApiService
import com.towich.vktest.util.ApiResult

class MainRepositoryImpl(
    private val appContext: Application,
    private val apiService: ApiService,
    private val sessionStorage: SessionStorage
): MainRepository {
    override suspend fun getCategories(): ApiResult<List<String>> {
        return apiService.getCategories()
    }

    override suspend fun getProducts(): ApiResult<List<ProductUIModel>> {
        return apiService.getProducts()
    }

    override suspend fun getProducts(skip: Int, category: String?): ApiResult<List<ProductUIModel>> {
        return apiService.getProducts(skip = skip, category = category)
    }

    override suspend fun getProductsByQuery(query: String): ApiResult<List<ProductUIModel>> {
        return apiService.getProductsByQuery(query = query)
    }

    override suspend fun getProductsByCategory(category: String): ApiResult<List<ProductUIModel>> {
        return apiService.getProductsByCategory(category = category)
    }

    override fun getCurrentProduct(): ProductUIModel? {
        return sessionStorage.currentProduct
    }

    override fun setCurrentProduct(newProduct: ProductUIModel) {
        sessionStorage.currentProduct = newProduct
    }


}