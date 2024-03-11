package com.towich.vktest.network


import com.towich.vktest.data.model.ProductUIModel
import com.towich.vktest.data.source.Constants
import com.towich.vktest.network.serializable.ListOfProductsSerializable
import com.towich.vktest.util.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import java.io.IOException


class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {
    override suspend fun getCategories(): ApiResult<List<String>> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.PRODUCTS + ApiRoutes.CATEGORIES

        try {
            val response = client.get(url)

            return when(response.status.value){
                in 200..299 -> {
                    ApiResult.Success(response.body())
                }

                else -> {
                    ApiResult.Error("Code ${response.status.value}: ${response.status.description}")
                }
            }
        } catch (e: Exception) {
            return ApiResult.Error("${e.message}")
        }
    }

    override suspend fun getProducts(): ApiResult<List<ProductUIModel>> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.PRODUCTS

        try {
            val response = client.get(url) {
                url {
                    parameters.append("limit", Constants.LIMIT_PRODUCTS.toString())
                }
            }

            return when(response.status.value){
                in 200..299 -> {
                    ApiResult.Success(response.body<ListOfProductsSerializable>().products.map { it.convertToUIModel() })
                }

                else -> {
                    ApiResult.Error("Code ${response.status.value}: ${response.status.description}")
                }
            }
        } catch (e: Exception) {
            return ApiResult.Error("${e.message}")
        }
    }

    override suspend fun getProducts(skip: Int, category: String?): ApiResult<List<ProductUIModel>> {
        var url = ApiRoutes.BASE_URL + ApiRoutes.PRODUCTS
        if(category != null){
            url += ApiRoutes.CATEGORY
        }


        try {
            val response = client.get(url) {
                url {
                    if(category != null){ appendPathSegments(category) }
                    parameters.append("skip", skip.toString())
                    parameters.append("limit", Constants.LIMIT_PRODUCTS.toString())
                }
            }


            return when(response.status.value){
                in 200..299 -> {
                    ApiResult.Success(response.body<ListOfProductsSerializable>().products.map { it.convertToUIModel() })
                }

                else -> {
                    ApiResult.Error("Code ${response.status.value}: ${response.status.description}")
                }
            }
        } catch (e: Exception) {
            return ApiResult.Error("${e.message}")
        }
    }

    override suspend fun getProductsByQuery(query: String): ApiResult<List<ProductUIModel>> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.PRODUCTS + ApiRoutes.SEARCH

        try {
            val response = client.get(url) {
                url {
                    parameters.append("q", query)
                }
            }

            if(response.body<ListOfProductsSerializable>().products.isEmpty()){
                return ApiResult.Error("Empty list!")
            }

            return when(response.status.value){
                in 200..299 -> {
                    ApiResult.Success(response.body<ListOfProductsSerializable>().products.map { it.convertToUIModel() })
                }

                else -> {
                    ApiResult.Error("Code ${response.status.value}: ${response.status.description}")
                }
            }
        } catch (e: Exception) {
            return ApiResult.Error("${e.message}")
        }
    }

    override suspend fun getProductsByCategory(category: String): ApiResult<List<ProductUIModel>> {
        val url = ApiRoutes.BASE_URL + ApiRoutes.PRODUCTS + ApiRoutes.CATEGORY

        try {
            val response = client.get(url) {
                url {
                    appendPathSegments(category)
                }
            }

            return when(response.status.value){
                in 200..299 -> {
                    ApiResult.Success(response.body<ListOfProductsSerializable>().products.map { it.convertToUIModel() })
                }

                else -> {
                    ApiResult.Error("Code ${response.status.value}: ${response.status.description}")
                }
            }
        } catch (e: Exception) {
            return ApiResult.Error("${e.message}")
        }
    }
}