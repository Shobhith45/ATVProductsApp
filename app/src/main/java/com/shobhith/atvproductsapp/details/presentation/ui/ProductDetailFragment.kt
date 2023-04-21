package com.shobhith.atvproductsapp.details.presentation.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.shobhith.atvproductsapp.R
import com.shobhith.atvproductsapp.common.util.DensityUtil
import com.shobhith.atvproductsapp.details.presentation.presenters.ProductDetailsDescriptionPresenter
import com.shobhith.atvproductsapp.details.presentation.state.ProductDetailsState
import com.shobhith.atvproductsapp.details.presentation.viewmodel.ProductDetailViewModel
import com.shobhith.atvproductsapp.details.util.ProductDetailConstants.ACTION_BUY
import com.shobhith.atvproductsapp.details.util.ProductDetailConstants.ACTION_PLAY
import com.shobhith.atvproductsapp.details.util.ProductDetailConstants.ACTION_RENT
import com.shobhith.atvproductsapp.details.util.ProductDetailConstants.RELATED_HEADER_ID
import com.shobhith.atvproductsapp.home.domain.model.Product
import com.shobhith.atvproductsapp.home.presentation.presenters.ProductCardPresenter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : DetailsSupportFragment() {

    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var mainAdapter: ArrayObjectAdapter
    private lateinit var controller: DetailsSupportFragmentBackgroundController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeDetailsState()
    }

    private fun observeDetailsState() {
        viewModel.detailsState.observe(viewLifecycleOwner) {
            when (it) {
                is ProductDetailsState.DetailsFetched -> {
                    bindDetailsData(it.details)
                }
                is ProductDetailsState.Error -> {
                    Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                is ProductDetailsState.RelatedItemsFetched -> {
                    bindRelatedItems(it.products)
                }
            }
        }
    }

    private fun bindRelatedItems(products: List<Product>?) {
        if (products != null) {
            val header = HeaderItem(RELATED_HEADER_ID, getString(R.string.related_products_header))
            val itemAdapter = createRelatedItemsRow(products)
            mainAdapter.add(ListRow(header, itemAdapter))
        }
    }

    private fun createRelatedItemsRow(products: List<Product>): ArrayObjectAdapter {
        val rowAdapter = ArrayObjectAdapter(ProductCardPresenter())
        for (product in products) {
            rowAdapter.add(product)
        }
        return rowAdapter
    }

    private fun bindDetailsData(details: Product?) {
        if (details != null) {
            loadBackgroundCoverImage(details.thumbnail)
            val actionAdapter = createActions(details)
            val detailsOverviewRow = createDetailsRow(details)
            detailsOverviewRow.actionsAdapter = actionAdapter

            mainAdapter.add(detailsOverviewRow)
        }
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

    private fun createDetailsRow(details: Product): DetailsOverviewRow {

        val width = DensityUtil.convertDpToPixel(requireContext(), resources.getDimension(R.dimen.banner_image_width)).toInt()
        val height = DensityUtil.convertDpToPixel(requireContext(), resources.getDimension(R.dimen.banner_image_height)).toInt()

        return DetailsOverviewRow(details).apply {
            Glide
                .with(this@ProductDetailFragment)
                .load(details.thumbnail)
                .centerCrop()
                .override(width, height)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        this@apply.imageDrawable = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    private fun loadBackgroundCoverImage(url: String) {
        Glide
            .with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    controller.coverBitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun createActions(product: Product): ArrayObjectAdapter {
        val actionsList = listOf(
            Action(
                ACTION_PLAY,
                getString(R.string.action_watch_for_free)
            ),
            Action(
                ACTION_RENT,
                getString(R.string.action_rent_by_day, product.price)
            ),
            Action(
                ACTION_BUY,
                getString(R.string.action_buy_and_own, product.price)
            )
        )
        val actionAdapter = ArrayObjectAdapter().apply {
            addAll(0, actionsList)
        }
        return actionAdapter
    }
}

