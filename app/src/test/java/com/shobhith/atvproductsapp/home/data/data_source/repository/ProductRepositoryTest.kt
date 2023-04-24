package com.shobhith.atvproductsapp.home.data.data_source.repository

import com.shobhith.atvproductsapp.home.data.data_source.remote.api.ProductApiService
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.Product
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {

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
        every { productApi.getCategories() } returns Observable.just(CategoryResponse().apply { addAll(categories) })
        val response = productRepository.getProductCategories().test()
        response.assertValue {
            it.containsAll(categories)
        }
    }

    @Test
    fun `given getProductCategories when error should return error response`() {
        val error = Throwable("Error")
        every { productApi.getCategories() } returns Observable.error(error)
        val response = productRepository.getProductCategories().test()
        response.assertError(error)
    }

    @Test
    fun `given getProductByCategoryName when success should return success response`() {
        val productResponse = ProductResponse(5, products, 2, 5)
        every { productApi.getProductByCategory(any()) } returns Observable.just(productResponse)
        val response = productRepository.getProductByCategoryName("smartphones").test()
        response.assertValue(productResponse)
    }

    @Test
    fun `given getProductByCategoryName when error should return error response`() {
        val error = Throwable("Error")
        every { productApi.getProductByCategory(any()) } returns Observable.error(error)
        val response = productRepository.getProductByCategoryName("smartphones").test()
        response.assertError(error)
    }
}