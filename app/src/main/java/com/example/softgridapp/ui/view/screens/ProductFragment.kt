package com.example.softgridapp.ui.view.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.softgridapp.R
import com.example.softgridapp.data.model.homeresponse_model.Product
import com.example.softgridapp.databinding.FragmentProductBinding
import com.example.softgridapp.ui.view.adapters.ProductListAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProductFragment : Fragment() {
    companion object {
        private const val ARG_PRODUCTS = "arg_products"

        fun newInstance(products: List<Product>): ProductFragment {
            val args = Bundle()
            args.putParcelableArrayList(ARG_PRODUCTS, ArrayList(products))

            val fragment = ProductFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val products: List<Product> by lazy {
        arguments?.getParcelableArrayList(ARG_PRODUCTS) ?: emptyList()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProductBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product, container, false
        )

        val adapter = ProductListAdapter(products)

        binding.recyclerView.adapter = adapter

        return binding.root
    }
}