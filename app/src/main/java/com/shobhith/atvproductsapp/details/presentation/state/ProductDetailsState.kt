package com.shobhith.atvproductsapp.details.presentation.state

import com.shobhith.atvproductsapp.home.domain.model.Product

sealed class ProductDetailsState(
    val details: Product? = null,
    val products: List<Product>? = null,
    val message: String? = null
) {
    data class DetailsFetched(private val content: Product) : ProductDetailsState(details = content)
    data class RelatedItemsFetched(private val items: List<Product>) :
        ProductDetailsState(products = items)

    data class Error(private val error: String?) : ProductDetailsState(message = error)
}

