package com.example.softgridapp.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.softgridapp.R
import com.example.softgridapp.data.model.homeresponse_model.Product
import com.example.softgridapp.databinding.LayoutProductBinding

class ProductListAdapter(private val list :List<Product>): RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: LayoutProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productTitle.text=product.name
            Glide.with(binding.root.context).load(product.image).into(binding.productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutProductBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_product, parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(list[position])
    }
}