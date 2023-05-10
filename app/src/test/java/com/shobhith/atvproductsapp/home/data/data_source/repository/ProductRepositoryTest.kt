package com.shobhith.atvproductsapp.home.data.data_source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shobhith.atvproductsapp.home.data.data_source.remote.api.ProductApiService
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.Product
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository
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
class ProductRepositoryTest {

    @get: Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @MockK
    private lateinit var productApi: ProductApiService

    private lateinit var productRepository: ProductRepository

    private val categories = listOf("smartphones", "laptops","skincare")
    private val products = listOf(
        Product("Apple","smartphones","iphone",12.9,1, listOf(), 499, 4.1, 94, "", "iPhoneX"),
        Product("Apple","smartphones","iphone",11.3,2, listOf(), 299, 3.8, 34, "", "iPhone9")
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        productRepository = ProductRepositoryImpl(productApi)
    }

    @Test
    fun `given getProductCategories when success should return success response`() {
        runTest {
            coEvery { productApi.getCategories() } returns Response.success((CategoryResponse().apply { addAll(categories) }))
            val response = productRepository.getProductCategories()
            assertThat(response.body()).isEqualTo(categories)
        }
    }

    @Test
    fun `given getProductCategories when error should return error response`() {
        runTest {
            coEvery { productApi.getCategories() } returns Response.error(400, "".toResponseBody("application/json".toMediaTypeOrNull()))
            val response = productRepository.getProductCategories()
            assertThat(response.code()).isEqualTo(400)
        }
    }

    @Test
    fun `given getProductByCategoryName when success should return success response`() {
        runTest {
            val productResponse = ProductResponse(5, products, 2, 5)
            coEvery { productApi.getProductByCategory(any()) } returns Response.success(productResponse)
            val response = productRepository.getProductByCategoryName("smartphones")
            assertThat(response.body()).isEqualTo(productResponse)
        }
    }

    @Test
    fun `given getProductByCategoryName when error should return error response`() {
        runTest {
            coEvery { productApi.getProductByCategory(any()) } returns Response.error(400, "".toResponseBody("application/json".toMediaTypeOrNull()))
            val response = productRepository.getProductByCategoryName("")
            assertThat(response.code()).isEqualTo(400)
        }
    }
}

