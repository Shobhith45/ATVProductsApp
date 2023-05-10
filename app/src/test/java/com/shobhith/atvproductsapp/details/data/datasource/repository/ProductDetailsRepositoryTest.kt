package com.shobhith.atvproductsapp.details.data.datasource.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shobhith.atvproductsapp.details.data.datasource.remote.api.ProductDetailsApiService
import com.shobhith.atvproductsapp.details.domain.repository.ProductDetailsRepository
import com.shobhith.atvproductsapp.home.domain.model.Product
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @MockK
    private lateinit var detailsApiService: ProductDetailsApiService

    private lateinit var productRepository: ProductDetailsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        productRepository = ProductDetailsRepositoryImpl(detailsApiService)
    }

    @Test
    fun `given getProductDetails when success returns success response`() {
        runTest {
            val product = Product("Apple","smartphones","iphone",12.9,1, listOf(), 499, 4.1, 94, "", "iPhoneX")
            coEvery { detailsApiService.getProductDetails(any()) } returns Response.success(product)
            val response = productRepository.getProductDetails(1)
            assertThat(response.body()).isEqualTo(product)
        }
    }

    @Test
    fun `given getProductDetails when error returns error response`() {
        runTest {
            val error = Throwable("Some Error")
            coEvery { detailsApiService.getProductDetails(any()) } returns Response.error(400, "".toResponseBody("application/json".toMediaTypeOrNull()))
            val response = productRepository.getProductDetails(0)
            assertThat(response.code()).isEqualTo(400)
        }
    }
}

