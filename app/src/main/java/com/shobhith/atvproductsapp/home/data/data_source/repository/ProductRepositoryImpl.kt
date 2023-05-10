package com.shobhith.atvproductsapp.home.data.data_source.repository

import com.shobhith.atvproductsapp.home.data.data_source.remote.api.ProductApiService
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository
import retrofit2.Response

class ProductRepositoryImpl(private val productApi: ProductApiService) : ProductRepository {
    override suspend fun getProductCategories(): Response<CategoryResponse>  =
        productApi.getCategories()

    override suspend fun getProductByCategoryName(name: String): Response<ProductResponse> =
        productApi.getProductByCategory(name)

}

