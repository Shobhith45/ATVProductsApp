package com.shobhith.atvproductsapp.details.presentation.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
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
                tvBrand.text = product.brand
                tvDescription.text = product.description
            }
        }
    }
}

