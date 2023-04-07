package com.shobhith.atvproductsapp.common.ui

import android.app.Application
import com.shobhith.atvproductsapp.common.di.appModule
import com.shobhith.atvproductsapp.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProductsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ProductsApplication)
            modules(appModule, homeModule)
        }
    }
}

