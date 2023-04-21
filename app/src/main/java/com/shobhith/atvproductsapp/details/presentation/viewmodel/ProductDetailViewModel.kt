package com.shobhith.atvproductsapp.details.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_CATEGORY
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_ID
import com.shobhith.atvproductsapp.details.domain.usecase.GetProductDetails
import com.shobhith.atvproductsapp.details.util.ProductDetailConstants.DEFAULT_CATEGORY
import com.shobhith.atvproductsapp.details.util.ProductDetailConstants.DEFAULT_ID
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductByCategoryName
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductDetailViewModel(
    private val getProductDetails: GetProductDetails,
    private val getProductByName: GetProductByCategoryName,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId = savedStateHandle.get<Int>(EXTRA_PRODUCT_ID) ?: DEFAULT_ID
    private val categoryName = savedStateHandle.get<String>(EXTRA_PRODUCT_CATEGORY) ?: DEFAULT_CATEGORY

    init {
        getProductDetailsById(productId)
    }

    fun getProductDetailsById(id: Int) {
        getProductDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {

                },
                {

                }
            )
    }

    fun getRelatedProducts(categoryName: String) {
        getProductByName(categoryName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {

                } ,
                {

                }
            )
    }
}

