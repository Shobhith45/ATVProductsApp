package com.shobhith.atvproductsapp.home.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shobhith.atvproductsapp.RxSchedulerRule
import com.shobhith.atvproductsapp.getOrAwaitValue
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.Product
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductByCategoryName
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductCategories
import com.shobhith.atvproductsapp.home.presentation.state.ProductListState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get: Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @get: Rule
    var rxSchedulerRule = RxSchedulerRule()

    @MockK
    private lateinit var getCategories: GetProductCategories

    @MockK
    private lateinit var getProduct: GetProductByCategoryName

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = HomeViewModel(getCategories, getProduct)
    }

    @Test
    fun `given get products when success should return ProductsFetched state with list of products`() {
        val products = listOf(
            Product("Apple","smartphones","iphone",12.9,1, listOf(), 499, 4.1, 94, "", "iPhoneX"),
            Product("Apple","smartphones","iphone",11.3,2, listOf(), 299, 3.8, 34, "", "iPhone9")
        )
        val productResponse = ProductResponse(5, products, 0, 5)
        val categories = CategoryResponse().apply {
            addAll(listOf("smartphones"))
        }
        every { getCategories() } returns Observable.just(categories)
        every { getProduct(any()) } returns Observable.just(productResponse)
        viewModel.getProducts()
        val actualState = viewModel.productHomeState.getOrAwaitValue()
        assertThat(actualState.products?.containsAll(products)).isTrue()
    }

    @Test
    fun `given get products when error should return Error state`() {
        every { getCategories() } returns Observable.error(Throwable(""))
        viewModel.getProducts()
        val actualState = viewModel.productHomeState.getOrAwaitValue()
        assertThat(actualState).isEqualTo(ProductListState.Error(""))
    }
}