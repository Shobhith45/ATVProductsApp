package com.shobhith.atvproductsapp.details.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_CATEGORY
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_ID
import com.shobhith.atvproductsapp.details.domain.usecase.GetProductDetails
import com.shobhith.atvproductsapp.details.presentation.state.ProductDetailsState
import com.shobhith.atvproductsapp.details.util.ProductDetailConstants.DEFAULT_CATEGORY
import com.shobhith.atvproductsapp.details.util.ProductDetailConstants.DEFAULT_ID
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductByCategoryName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductDetails: GetProductDetails,
    private val getProductByName: GetProductByCategoryName,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId = savedStateHandle.get<Int>(EXTRA_PRODUCT_ID) ?: DEFAULT_ID
    private val categoryName = savedStateHandle.get<String>(EXTRA_PRODUCT_CATEGORY) ?: DEFAULT_CATEGORY

    private val _detailsState = MutableLiveData<ProductDetailsState>()
    val detailsState: LiveData<ProductDetailsState> get() = _detailsState

    init {
        getProductDetailsById(productId)
    }

    fun getProductDetailsById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getProductDetails(id)
                if (response.isSuccessful) {
                    _detailsState.postValue(ProductDetailsState.DetailsFetched(response.body()))
                    getRelatedProducts(categoryName)
                } else {
                    _detailsState.postValue(ProductDetailsState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                _detailsState.postValue(ProductDetailsState.Error(e.message))
            }
        }
    }

    fun getRelatedProducts(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getProductByName(categoryName)
                if (response.isSuccessful) {
                    _detailsState.postValue(
                        ProductDetailsState.RelatedItemsFetched(response.body()?.products?.shuffled())
                    )
                } else {
                    _detailsState.postValue(ProductDetailsState.Error(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                _detailsState.postValue(ProductDetailsState.Error(e.message))
            }
        }
    }
}

