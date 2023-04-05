package com.shobhith.atvproductsapp.home.di

import com.shobhith.atvproductsapp.home.data.data_source.remote.api.ProductApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    single { provideProductApi(get()) }
}

private fun provideProductApi(retrofit: Retrofit) : ProductApiService =
    retrofit.create(ProductApiService::class.java)