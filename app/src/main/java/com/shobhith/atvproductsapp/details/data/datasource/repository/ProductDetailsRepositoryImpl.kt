package com.shobhith.atvproductsapp.details.data.datasource.repository

import com.shobhith.atvproductsapp.details.data.datasource.remote.api.ProductDetailsApiService
import com.shobhith.atvproductsapp.details.domain.repository.ProductDetailsRepository
import com.shobhith.atvproductsapp.home.domain.model.Product
import retrofit2.Response

class ProductDetailsRepositoryImpl(
    private val detailsApiService: ProductDetailsApiService
) : ProductDetailsRepository {
    override suspend fun getProductDetails(id: Int): Response<Product> =
        detailsApiService.getProductDetails(id)
}

