package com.towich.vktest.network


import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.data.source.Constants
import com.towich.vktest.network.ApiService
import com.towich.vktest.network.serializable.ListOfProductsSerializable
import com.towich.vktest.network.serializable.ProductSerializable
import com.towich.vktest.ui.screen.main.MainScreenUiState
import com.towich.vktest.util.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import java.io.IOException


class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {
    override suspend fun getProducts(): ApiResult<List<ProductUIModel>> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.PRODUCTS + "?" + "limit=${Constants.LIMIT_PRODUCTS}"

        try {
            val response = client.get {
                url(url)
            }

            return when(response.status.value){
                in 200..299 -> {
                    ApiResult.Success(response.body<ListOfProductsSerializable>().products.map { it.convertToUIModel() })
                }

                else -> {
                    ApiResult.Error("Code ${response.status.value}: ${response.status.description}")
                }
            }

        } catch (e: IOException) {
            return ApiResult.Error("No connection!")
        } catch (e: Exception) {
            return ApiResult.Error("${e.message}")
        }
    }

    override suspend fun getProducts(skip: Int): ApiResult<List<ProductUIModel>> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.PRODUCTS + "?" + "skip=$skip" + "&" + "limit=${Constants.LIMIT_PRODUCTS}"

        try {
            val response = client.get {
                url(url)
            }

            return when(response.status.value){
                in 200..299 -> {
                    ApiResult.Success(response.body<ListOfProductsSerializable>().products.map { it.convertToUIModel() })
                }

                else -> {
                    ApiResult.Error("Code ${response.status.value}: ${response.status.description}")
                }
            }

        } catch (e: IOException) {
            return ApiResult.Error("No connection!")
        } catch (e: Exception) {
            return ApiResult.Error("${e.message}")
        }
    }
}