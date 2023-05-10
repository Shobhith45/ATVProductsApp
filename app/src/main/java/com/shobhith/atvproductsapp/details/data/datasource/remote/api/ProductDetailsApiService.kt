package com.shobhith.atvproductsapp.details.data.datasource.remote.api

import com.shobhith.atvproductsapp.home.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsApiService {
    @GET("/products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int) : Response<Product>
}

