package com.shobhith.atvproductsapp.home.domain.use_case

import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository

class GetProductCategories(private val repository: ProductRepository) {
    operator fun invoke() =
        repository.getProductCategories()
}