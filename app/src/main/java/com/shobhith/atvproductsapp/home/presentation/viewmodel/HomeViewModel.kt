package com.shobhith.atvproductsapp.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductByCategoryName
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductCategories
import com.shobhith.atvproductsapp.home.presentation.state.ProductListState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(
    private val getCategories: GetProductCategories,
    private val getProductByCategoryName: GetProductByCategoryName
) : ViewModel() {

    private val _productHomeState = MutableLiveData<ProductListState>()
    val productHomeState: LiveData<ProductListState> get() = _productHomeState

    init {
        getProducts()
    }

    private fun getProducts() {
        getCategories()
            .subscribeOn(Schedulers.io())
            .flatMap { catList -> Observable.fromIterable(catList) }
            .concatMap { catName -> getProductByCategoryName(catName) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _productHomeState.value = ProductListState.ProductsFetched(it.products)
                },
                {
                    _productHomeState.value = ProductListState.Error(it.message)
                }
            )
    }

}

