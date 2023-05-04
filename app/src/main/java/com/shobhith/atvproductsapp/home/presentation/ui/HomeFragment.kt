package com.shobhith.atvproductsapp.home.presentation.ui

import android.content.Intent
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
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_CATEGORY
import com.shobhith.atvproductsapp.common.util.Constants.EXTRA_PRODUCT_ID
import com.shobhith.atvproductsapp.details.presentation.ui.ProductDetailActivity
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
        setOnProductClickListener()
    }

    private fun setOnProductClickListener() {
        setOnItemViewClickedListener { _, item, _, _ ->
            if (item is Product) {
                val intent = Intent(activity, ProductDetailActivity::class.java)
                intent.putExtra(EXTRA_PRODUCT_ID, item.id)
                intent.putExtra(EXTRA_PRODUCT_CATEGORY, item.category)
                startActivity(intent)
            }
        }
    }

    private fun setupAdapter() {
        val listPresenter = ListRowPresenter(FocusHighlight.ZOOM_FACTOR_XSMALL).apply {
            shadowEnabled = false
        }
        rowsAdapter = ArrayObjectAdapter(listPresenter)
        adapter = rowsAdapter
    }

    private fun observeData() {
        viewModel.productHomeState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ProductListState.Error -> {
                    Toast.makeText(activity?.applicationContext, state.errorMessage.toString(), Toast.LENGTH_LONG).show()
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

