package com.shobhith.atvproductsapp.details.domain.repository

import com.shobhith.atvproductsapp.home.domain.model.Product
import retrofit2.Response

interface ProductDetailsRepository {
    suspend fun getProductDetails(id: Int) : Response<Product>
}

