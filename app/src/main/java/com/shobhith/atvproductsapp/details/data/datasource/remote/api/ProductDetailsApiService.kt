package com.shobhith.atvproductsapp.details.data.datasource.remote.api

import com.shobhith.atvproductsapp.home.domain.model.Product
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsApiService {
    @GET("/products/{id}")
    fun getProductDetails(@Path("id") id: Int) : Observable<Product>
}

