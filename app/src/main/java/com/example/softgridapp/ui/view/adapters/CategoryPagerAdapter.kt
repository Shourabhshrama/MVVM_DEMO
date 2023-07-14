package com.example.softgridapp.ui.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.softgridapp.data.model.homeresponse_model.Category
import com.example.softgridapp.ui.view.screens.ProductFragment

class CategoryPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,private val categories: List<Category>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val category = categories[position]
        return ProductFragment.newInstance(category.products)
    }


}