package com.shobhith.atvproductsapp.home.domain.repository

import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import retrofit2.Response

interface ProductRepository {
    suspend fun getProductCategories() : Response<CategoryResponse>
    suspend fun getProductByCategoryName(name: String) : Response<ProductResponse>
}

