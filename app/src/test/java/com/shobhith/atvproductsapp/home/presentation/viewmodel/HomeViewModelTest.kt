package com.shobhith.atvproductsapp.home.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shobhith.atvproductsapp.getOrAwaitValue
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.Product
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductByCategoryName
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductCategories
import com.shobhith.atvproductsapp.home.presentation.state.ProductListState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get: Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCategories: GetProductCategories

    @MockK
    private lateinit var getProduct: GetProductByCategoryName

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this, relaxed = true)
        viewModel = HomeViewModel(getCategories, getProduct)
    }

    @Test
    fun `given get products when success should return ProductsFetched state with list of products`() {
        runTest {
            val products = listOf(
                Product("Apple","smartphones","iphone",12.9,1, listOf(), 499, 4.1, 94, "", "iPhoneX"),
                Product("Apple","smartphones","iphone",11.3,2, listOf(), 299, 3.8, 34, "", "iPhone9")
            )
            val productResponse = ProductResponse(5, products, 0, 5)
            val categories = CategoryResponse().apply {
                addAll(listOf("smartphones"))
            }
            coEvery { getCategories() } returns Response.success(categories)
            coEvery { getProduct(any()) } returns Response.success(productResponse)
            viewModel.getProducts()
            val actualState = viewModel.productHomeState.getOrAwaitValue()
            assertThat(actualState.products?.containsAll(products)).isTrue()
        }
    }

    @Test
    fun `given get products when error should return Error state`() {
        runTest {
            coEvery { getCategories() } returns Response.error(400, "Some Error".toResponseBody("application/json".toMediaTypeOrNull()))
            viewModel.getProducts()
            val actualState = viewModel.productHomeState.getOrAwaitValue()
            assertThat(actualState).isEqualTo(ProductListState.Error("Some Error"))
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}