package com.towich.vktest.network.serializable

import com.towich.vktest.data.model.ProductUIModel
import kotlinx.serialization.Serializable

@Serializable
data class ListOfProductsSerializable(
    val products: List<ProductSerializable>
)