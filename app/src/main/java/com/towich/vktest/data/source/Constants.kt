package com.towich.vktest.data.source

import com.towich.vktest.data.model.ProductUIModel

object Constants {
    const val LIMIT_PRODUCTS = 20
    val errorProduct = ProductUIModel(
        id = 0,
        title = "error",
        description = "error",
        price = 0,
        discountPercentage = 0f,
        rating = 0f,
        stock = 0,
        brand = "error",
        category = "error",
        thumbnail = "error",
        images = listOf(
            "error",
            "error",
            "error",
            "error",
        )
    )
}