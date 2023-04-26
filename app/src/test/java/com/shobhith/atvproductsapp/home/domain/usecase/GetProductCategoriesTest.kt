package com.shobhith.atvproductsapp.home.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetProductCategoriesTest {

    @get: Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @MockK
    private lateinit var productRepository: ProductRepository

    private lateinit var getProductCategories: GetProductCategories

    private val categories = listOf("smartphones", "laptops","skincare")

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getProductCategories = GetProductCategories(productRepository)
    }

    @Test
    fun `given getProductCategories when success should return category list`() {
        every { productRepository.getProductCategories() } returns Observable.just(CategoryResponse().apply { addAll(categories) })
        val response = getProductCategories().test()
        response.assertValue {
            it.containsAll(categories)
        }
    }

    @Test
    fun `given getProductCategories when error should return observable error`() {
        val error = Throwable("Some Error")
        every { productRepository.getProductCategories() } returns Observable.error(error)
        val response = getProductCategories().test()
        response.assertError(error)
    }
}

