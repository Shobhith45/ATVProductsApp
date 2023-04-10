package com.shobhith.atvproductsapp.home.domain.usecase

import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository

class GetProductByCategoryName(private val repository: ProductRepository) {
    operator fun invoke(name: String) =
        repository.getProductByCategoryName(name)
}

