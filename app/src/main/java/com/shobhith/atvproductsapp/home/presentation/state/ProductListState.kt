package com.shobhith.atvproductsapp.home.presentation.state

import com.shobhith.atvproductsapp.home.domain.model.Product

sealed class ProductListState(val products: List<Product>? = null, val errorMessage: String? = null) {

    data class ProductsFetched(private val productList: List<Product>) : ProductListState(products = productList)

    data class Error(private val message: String?) : ProductListState(errorMessage = message)
}

