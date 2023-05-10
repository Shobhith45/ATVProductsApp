package com.shobhith.atvproductsapp.home.data.data_source.remote.api

import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("/products/categories")
    suspend fun getCategories() : Response<CategoryResponse>

    @GET("/products/category/{category}")
    suspend fun getProductByCategory(@Path("category")category: String) : Response<ProductResponse>

}

