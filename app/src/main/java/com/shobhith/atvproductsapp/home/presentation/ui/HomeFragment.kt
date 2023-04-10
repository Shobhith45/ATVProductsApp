package com.shobhith.atvproductsapp.home.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import com.shobhith.atvproductsapp.R

class HomeFragment : BrowseSupportFragment() {

    private val backgroundManager by lazy {
        BackgroundManager.getInstance(requireActivity()).apply {
            attach(requireActivity().window)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        title = getString(R.string.home_page_title)
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        brandColor = ResourcesCompat.getColor(resources, R.color.background_color, null)
        backgroundManager.color = ResourcesCompat.getColor(resources, R.color.background_color, null)
    }
}