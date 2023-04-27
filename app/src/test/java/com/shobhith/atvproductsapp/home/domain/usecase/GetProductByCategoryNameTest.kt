package com.shobhith.atvproductsapp.home.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.model.Product
import com.shobhith.atvproductsapp.home.domain.model.ProductResponse
import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        val productResponse = ProductResponse(5, products, 2, 5)
        every { productRepository.getProductByCategoryName(any()) } returns Observable.just(productResponse)
        val response = getProducts("smartphones").test()
        response.assertValue {
            it.products.containsAll(products)
        }
    }

    @Test
    fun `given GetProductByCategoryName when error should return error observable`() {
        val error = Throwable("Some Error")
        every { productRepository.getProductByCategoryName(any()) } returns Observable.error(error)
        val response = getProducts("").test()
        response.assertError(error)
    }
}

