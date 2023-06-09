package com.shobhith.atvproductsapp.details.domain.usecase

import com.shobhith.atvproductsapp.details.domain.repository.ProductDetailsRepository

class GetProductDetails(private val detailsRepository: ProductDetailsRepository) {
    operator fun invoke(id: Int) =
        detailsRepository.getProductDetails(id)
}

