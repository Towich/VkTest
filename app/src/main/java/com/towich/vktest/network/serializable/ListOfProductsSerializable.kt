package com.towich.vktest.network.serializable

import kotlinx.serialization.Serializable

@Serializable
data class ListOfProductsSerializable(
    val products: List<ProductSerializable>
)