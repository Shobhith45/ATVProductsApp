package com.shobhith.atvproductsapp.home.di

import com.shobhith.atvproductsapp.home.data.data_source.remote.api.ProductApiService
import com.shobhith.atvproductsapp.home.data.data_source.repository.ProductRepositoryImpl
import com.shobhith.atvproductsapp.home.domain.repository.ProductRepository
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductByCategoryName
import com.shobhith.atvproductsapp.home.domain.usecase.GetProductCategories
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    single { provideProductApi(get()) }
    factory { provideProductRepository(get()) }
    factory { provideGetCategoriesUseCase(get()) }
    factory { provideGetProductByCategoryNameUseCase(get()) }
}

private fun provideProductApi(retrofit: Retrofit) : ProductApiService =
    retrofit.create(ProductApiService::class.java)

private fun provideProductRepository(api: ProductApiService) : ProductRepository =
    ProductRepositoryImpl(api)

private fun provideGetCategoriesUseCase(repository: ProductRepository) : GetProductCategories =
    GetProductCategories(repository)

private fun provideGetProductByCategoryNameUseCase(repository: ProductRepository) : GetProductByCategoryName =
    GetProductByCategoryName(repository)
