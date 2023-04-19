package com.shobhith.atvproductsapp.details.data.datasource.repository

import com.shobhith.atvproductsapp.details.data.datasource.remote.api.ProductDetailsApiService
import com.shobhith.atvproductsapp.details.domain.repository.ProductDetailsRepository
import com.shobhith.atvproductsapp.home.domain.model.Product
import io.reactivex.rxjava3.core.Observable

class ProductDetailsRepositoryImpl(
    private val detailsApiService: ProductDetailsApiService
) : ProductDetailsRepository {
    override fun getProductDetails(id: Int): Observable<Product> =
        detailsApiService.getProductDetails(id)
}

