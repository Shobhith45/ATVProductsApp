package com.shobhith.atvproductsapp.details.presentation.presenters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import com.shobhith.atvproductsapp.R
import com.shobhith.atvproductsapp.databinding.ItemProductDetailsBinding
import com.shobhith.atvproductsapp.home.domain.model.Product

class ProductDetailsDescriptionPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemProductDetailsBinding.inflate(inflater, parent, false)
        return ProductDescriptionViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        if (item is Product) {
            val holder = viewHolder as ProductDescriptionViewHolder
            holder.bind(item)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }

    inner class ProductDescriptionViewHolder(private val binding: ItemProductDetailsBinding) : ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                tvTitle.text = product.title
                chipBrand.text = product.brand
                chipCategory.text = product.category
                tvDescription.text = product.description
                with(root.context) {
                    tvPrice.text = getString(R.string.product_price, product.price)
                    tvRating.rating = product.rating.toFloat()
                    tvDiscount.text = getString(R.string.discount_percentage, product.discountPercentage.toString())
                }
            }
        }
    }
}

