package com.shobhith.atvproductsapp.details.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shobhith.atvproductsapp.details.domain.repository.ProductDetailsRepository
import com.shobhith.atvproductsapp.home.domain.model.Product
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetProductDetailsTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @MockK
    private lateinit var productRepository: ProductDetailsRepository

    private lateinit var getProductDetails: GetProductDetails

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getProductDetails = GetProductDetails(productRepository)
    }

    @Test
    fun `given getProductDetails when success returns success response`() {
        val product = Product("Apple","smartphones","iphone",12.9,1, listOf(), 499, 4.1, 94, "", "iPhoneX")
        every { productRepository.getProductDetails(any()) } returns Observable.just(product)
        val response = getProductDetails(1).test()
        response.assertValue(product)
    }

    @Test
    fun `given getProductDetails when error returns error response`() {
        val error = Throwable("Some Error")
        every { productRepository.getProductDetails(any()) } returns Observable.error(error)
        val response = getProductDetails(0).test()
        response.assertError(error)
    }
}

