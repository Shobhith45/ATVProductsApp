package com.shobhith.atvproductsapp.home.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import com.shobhith.atvproductsapp.R
import com.shobhith.atvproductsapp.home.domain.model.Product
import com.shobhith.atvproductsapp.home.presentation.presenters.ProductCardPresenter
import com.shobhith.atvproductsapp.home.presentation.state.ProductListState
import com.shobhith.atvproductsapp.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BrowseSupportFragment() {

    private val backgroundManager by lazy {
        BackgroundManager.getInstance(requireActivity()).apply {
            attach(requireActivity().window)
        }
    }

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var rowsAdapter: ArrayObjectAdapter

    private var headerId = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupAdapter()
        observeData()
    }

    private fun setupAdapter() {
        rowsAdapter = ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_NONE))
        adapter = rowsAdapter
    }

    private fun observeData() {
        viewModel.productHomeState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ProductListState.Error -> {
                    Toast.makeText(context, state.errorMessage.toString(), Toast.LENGTH_LONG).show()
                }
                is ProductListState.ProductsFetched -> {
                    if ((state.products != null) && state.products.isNotEmpty()) {
                        createProductRows(state.products)
                    }
                }
            }
        }
    }

    private fun createProductRows(products: List<Product>) {
        val cardAdapter = ArrayObjectAdapter(ProductCardPresenter())
        val headerItem = HeaderItem(headerId++, products[0].category)
        for (product in products) {
            cardAdapter.add(product)
        }
        rowsAdapter.add(ListRow(headerItem, cardAdapter))
    }

    private fun setupUI() {
        title = getString(R.string.home_page_title)
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        brandColor = ResourcesCompat.getColor(resources, R.color.background_color, null)
        backgroundManager.color = ResourcesCompat.getColor(resources, R.color.background_color, null)
    }
}

