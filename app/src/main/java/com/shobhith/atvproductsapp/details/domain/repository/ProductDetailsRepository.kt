package com.shobhith.atvproductsapp.details.domain.repository

import com.shobhith.atvproductsapp.home.domain.model.Product
import io.reactivex.rxjava3.core.Observable

interface ProductDetailsRepository {
    fun getProductDetails(id: Int) : Observable<Product>
}

