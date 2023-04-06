package com.shobhith.atvproductsapp.home.domain.repository

import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import io.reactivex.rxjava3.core.Observable

interface ProductRepository {
    fun getProductCategories() : Observable<CategoryResponse>
    fun getProductByCategoryName(name: String) : Observable<ProductResponse>
}
