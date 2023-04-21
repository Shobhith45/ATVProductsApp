package com.shobhith.atvproductsapp.details.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import com.shobhith.atvproductsapp.R
import com.shobhith.atvproductsapp.details.presentation.presenter.ProductDetailsDescriptionPresenter
import com.shobhith.atvproductsapp.details.presentation.viewmodel.ProductDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : DetailsSupportFragment() {

    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var mainAdapter: ArrayObjectAdapter
    private lateinit var controller: DetailsSupportFragmentBackgroundController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        controller = DetailsSupportFragmentBackgroundController(this).apply {
            enableParallax()
        }

        val fullWidthMovieDetailsPresenter = FullWidthDetailsOverviewRowPresenter(ProductDetailsDescriptionPresenter()).apply {
                backgroundColor = ContextCompat.getColor(requireContext(), R.color.details_background_color)
        }

        val classPresenterSelector = ClassPresenterSelector().apply {
            addClassPresenter(DetailsOverviewRow::class.java, fullWidthMovieDetailsPresenter)
            addClassPresenter(ListRow::class.java, ListRowPresenter())
        }

        mainAdapter = ArrayObjectAdapter(classPresenterSelector)
        adapter = mainAdapter
    }
}

