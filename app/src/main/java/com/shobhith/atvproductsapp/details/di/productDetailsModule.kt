package com.shobhith.atvproductsapp.details.di

import com.shobhith.atvproductsapp.details.data.datasource.remote.api.ProductDetailsApiService
import com.shobhith.atvproductsapp.details.data.datasource.repository.ProductDetailsRepositoryImpl
import com.shobhith.atvproductsapp.details.domain.repository.ProductDetailsRepository
import com.shobhith.atvproductsapp.details.domain.usecase.GetProductDetails
import com.shobhith.atvproductsapp.details.presentation.viewmodel.ProductDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val productDetailsModule = module {
    single { provideProductDetailsApi(get()) }
    factory { provideProductDetailsRepository(get()) }
    factory { provideGetProductDetailsUseCase(get()) }
    viewModel { ProductDetailViewModel(get(), get(), get()) }
}

private fun provideProductDetailsApi(retrofit: Retrofit) : ProductDetailsApiService =
    retrofit.create(ProductDetailsApiService::class.java)

private fun provideProductDetailsRepository(detailsApi: ProductDetailsApiService) : ProductDetailsRepository =
    ProductDetailsRepositoryImpl(detailsApi)

private fun provideGetProductDetailsUseCase(detailsRepository: ProductDetailsRepository) : GetProductDetails =
    GetProductDetails(detailsRepository)

