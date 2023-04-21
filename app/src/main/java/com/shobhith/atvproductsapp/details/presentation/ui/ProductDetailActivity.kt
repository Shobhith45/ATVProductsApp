package com.shobhith.atvproductsapp.details.presentation.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.shobhith.atvproductsapp.R
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_CATEGORY
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_ID
import com.shobhith.atvproductsapp.details.util.ProductDetailConstants.DEFAULT_ID

class ProductDetailActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        if (savedInstanceState == null) {
            createProductDetailsFragment()
        }
    }

    private fun createProductDetailsFragment() {
        val detailsFragment = ProductDetailFragment()
        val bundle = Bundle().apply {
            putInt(EXTRA_PRODUCT_ID, intent.getIntExtra(EXTRA_PRODUCT_ID, DEFAULT_ID))
            putString(EXTRA_PRODUCT_CATEGORY, intent.getStringExtra(EXTRA_PRODUCT_CATEGORY))
        }
        detailsFragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details_container, detailsFragment)
            .commit()
    }
}

