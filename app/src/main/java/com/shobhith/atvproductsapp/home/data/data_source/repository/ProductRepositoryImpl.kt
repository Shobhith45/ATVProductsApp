package com.shobhith.atvproductsapp.home.data.data_source.repository

import com.shobhith.atvproductsapp.home.data.data_source.remote.api.ProductApiService
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository
import io.reactivex.rxjava3.core.Observable

class ProductRepositoryImpl(private val productApi: ProductApiService) : ProductRepository {
    override fun getProductCategories(): Observable<CategoryResponse>  =
        productApi.getCategories()

    override fun getProductByCategoryName(name: String): Observable<ProductResponse> =
        productApi.getProductByCategory(name)

}

