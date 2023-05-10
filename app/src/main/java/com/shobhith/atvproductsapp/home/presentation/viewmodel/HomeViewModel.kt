package com.shobhith.atvproductsapp.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductByCategoryName
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductCategories
import com.shobhith.atvproductsapp.home.presentation.state.ProductListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class HomeViewModel(
    private val getCategories: GetProductCategories,
    private val getProductByCategoryName: GetProductByCategoryName
) : ViewModel() {

    private val _productHomeState = MutableLiveData<ProductListState>()
    val productHomeState: LiveData<ProductListState> get() = _productHomeState

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getCategories()
                if (response.isSuccessful) {
                    getProductForEachCategory(response)
                } else {
                    _productHomeState.postValue(ProductListState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                _productHomeState.postValue(ProductListState.Error(e.message))
            }
        }
    }

    private suspend fun getProductForEachCategory(response: Response<CategoryResponse>) {
        response.body()?.forEach { category ->
            val productResponse = getProductByCategoryName(category)
            withContext(Dispatchers.Main) {
                _productHomeState.value =
                    ProductListState.ProductsFetched(productResponse.body()?.products)
            }
        }
    }

}

