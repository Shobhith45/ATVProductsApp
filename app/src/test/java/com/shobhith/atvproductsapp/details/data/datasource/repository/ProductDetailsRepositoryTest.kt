package com.shobhith.atvproductsapp.details.data.datasource.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shobhith.atvproductsapp.details.data.datasource.remote.api.ProductDetailsApiService
import com.shobhith.atvproductsapp.details.domain.repository.ProductDetailsRepository
import com.shobhith.atvproductsapp.home.domain.model.Product
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        val product = Product("Apple","smartphones","iphone",12.9,1, listOf(), 499, 4.1, 94, "", "iPhoneX")
        every { detailsApiService.getProductDetails(any()) } returns Observable.just(product)
        val response = productRepository.getProductDetails(1).test()
        response.assertValue(product)
    }

    @Test
    fun `given getProductDetails when error returns error response`() {
        val error = Throwable("Some Error")
        every { detailsApiService.getProductDetails(any()) } returns Observable.error(error)
        val response = productRepository.getProductDetails(0).test()
        response.assertError(error)
    }
}

