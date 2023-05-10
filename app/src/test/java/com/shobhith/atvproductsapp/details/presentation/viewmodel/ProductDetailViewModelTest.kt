package com.shobhith.atvproductsapp.details.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_CATEGORY
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_ID
import com.shobhith.atvproductsapp.details.domain.usecase.GetProductDetails
import com.shobhith.atvproductsapp.details.presentation.state.ProductDetailsState
import com.shobhith.atvproductsapp.getOrAwaitValue
import com.shobhith.atvproductsapp.home.domain.model.Product
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductByCategoryName
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @MockK
    private lateinit var getProductDetails: GetProductDetails

    @MockK
    private lateinit var getProductByName: GetProductByCategoryName

    private var savedStateHandle: SavedStateHandle = SavedStateHandle().apply {
        set(EXTRA_PRODUCT_ID, 1)
        set(EXTRA_PRODUCT_CATEGORY, "smartphones")
    }

    private lateinit var viewModel: ProductDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = ProductDetailViewModel(getProductDetails, getProductByName, savedStateHandle)
    }

    @Test
    fun `given getProductDetailsById when success should return DetailsFetched state`() {
        runTest {
            val product = Product("Apple","smartphones","iphone",12.9,1, listOf(), 499, 4.1, 94, "", "iPhoneX")
            coEvery { getProductDetails(any()) } returns Response.success(product)
            viewModel.getProductDetailsById(1)
            val actualState = viewModel.detailsState.getOrAwaitValue()
            assertThat(actualState.details).isEqualTo(product)
        }
    }

    @Test
    fun `given getProductDetailsById when error should return Error state`() {
        runTest {
            coEvery { getProductDetails(any()) } returns Response.error(400,
                "Some Error".toResponseBody("application/json".toMediaTypeOrNull())
            )
            viewModel.getProductDetailsById(0)
            val actualState = viewModel.detailsState.getOrAwaitValue()
            assertThat(actualState).isEqualTo(ProductDetailsState.Error("Some Error"))
        }
    }

    @Test
    fun `given getRelatedProducts when success should return RelatedItemsFetched state`() {
        runTest {
            val products = listOf(
                Product("Apple","smartphones","iphone",12.9,1, listOf(), 499, 4.1, 94, "", "iPhoneX"),
                Product("Apple","smartphones","iphone",11.3,2, listOf(), 299, 3.8, 34, "", "iPhone9")
            )
            val productResponse = ProductResponse(5, products, 0, 5)
            coEvery { getProductByName(any()) } returns Response.success(productResponse)
            viewModel.getRelatedProducts("smartphones")
            val actualState = viewModel.detailsState.getOrAwaitValue()
            assertThat(actualState.products?.containsAll(products)).isTrue()
        }
    }

    @Test
    fun `given getRelatedProducts when error should return Error state`() {
        runTest {
            coEvery { getProductByName(any()) } returns Response.error(400, "Some Error".toResponseBody("application/json".toMediaTypeOrNull()))
            viewModel.getRelatedProducts("")
            val actualState = viewModel.detailsState.getOrAwaitValue()
            assertThat(actualState).isEqualTo(ProductDetailsState.Error("Some Error"))
        }
    }
}

