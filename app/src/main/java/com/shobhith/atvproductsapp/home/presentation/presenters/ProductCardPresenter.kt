package com.shobhith.atvproductsapp.home.presentation.presenters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.shobhith.atvproductsapp.R
import com.shobhith.atvproductsapp.databinding.ItemProductBinding
import com.shobhith.atvproductsapp.home.domain.model.Product

class ProductCardPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemProductBinding.inflate(inflater, parent, false)
        return ProductCardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        if (item is Product) {
            val holder = viewHolder as ProductCardViewHolder
            holder.bind(item)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
    }

    inner class ProductCardViewHolder(private val binding: ItemProductBinding) : ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                Glide.with(root.context)
                    .load(product.thumbnail)
                    .into(ivBanner)
                tvTitle.text = product.title
                tvDescription.text = product.description
                tvRating.rating = product.rating.toFloat()
            }
        }
    }
}

