package com.shobhith.atvproductsapp.home.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
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
class GetProductByCategoryNameTest {

    @get: Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @MockK
    private lateinit var productRepository: ProductRepository

    private lateinit var getProducts: GetProductByCategoryName

    private val products = listOf(
        Product("Apple","smartphones","iphone",12.9,1, listOf(), 499, 4.1, 94, "", "iPhoneX"),
        Product("Apple","smartphones","iphone",11.3,2, listOf(), 299, 3.8, 34, "", "iPhone9")
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getProducts = GetProductByCategoryName(productRepository)
    }

    @Test
    fun `given GetProductByCategoryName when success should return products list`() {
        runTest {
            val productResponse = ProductResponse(5, products, 2, 5)
            coEvery { productRepository.getProductByCategoryName(any()) } returns Response.success(productResponse)
            val response = getProducts("smartphones")
            assertThat(response.body()).isEqualTo(productResponse)
        }
    }

    @Test
    fun `given GetProductByCategoryName when error should return error observable`() {
        runTest {
            val error = Throwable("Some Error")
            coEvery { productRepository.getProductByCategoryName(any()) } returns Response.error(400, "".toResponseBody("application/json".toMediaTypeOrNull()))
            val response = getProducts("")
            assertThat(response.code()).isEqualTo(400)
        }
    }
}

