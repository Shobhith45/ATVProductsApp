package com.shobhith.atvproductsapp.home.domain.use_case

import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository

class GetProductByCategoryName(private val repository: ProductRepository) {
    operator fun invoke(name: String) =
        repository.getProductByCategoryName(name)
}