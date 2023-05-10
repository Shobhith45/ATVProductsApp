package com.shobhith.atvproductsapp.home.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shobhith.atvproductsapp.home.domain.model.CategoryResponse
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
        runTest {
            coEvery { productRepository.getProductCategories() } returns Response.success(CategoryResponse().apply { addAll(categories) })
            val response = getProductCategories()
            assertThat(response.body()?.containsAll(categories)).isTrue()
        }
    }

    @Test
    fun `given getProductCategories when error should return observable error`() {
        runTest {
            coEvery { productRepository.getProductCategories() } returns Response.error(400, "".toResponseBody("application/json".toMediaTypeOrNull()))
            val response = getProductCategories()
            assertThat(response.code()).isEqualTo(400)
        }
    }
}

