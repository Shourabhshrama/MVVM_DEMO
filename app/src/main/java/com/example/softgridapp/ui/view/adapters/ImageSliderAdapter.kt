package com.example.softgridapp.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.softgridapp.R
import com.example.softgridapp.data.model.homeresponse_model.ImageSlider
import com.example.softgridapp.databinding.ItemImageSliderBinding

class ImageSliderAdapter(private val images: List<ImageSlider>) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            binding.imageUrl = imageUrl
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemImageSliderBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_image_slider, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = images[position].image
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
