package com.shobhith.atvproductsapp.home.data.data_source.remote.api

import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("/products/categories")
    fun getCategories() : Observable<CategoryResponse>

    @GET("/products/category/{category}")
    fun getProductByCategory(@Path("category")category: String) : Observable<ProductResponse>

}

