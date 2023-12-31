package com.example.shoppingapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.adapters.ProductsAdapter
import com.example.shoppingapp.databinding.FragmentProductsDetailsBinding
import com.example.shoppingapp.viewModel.ShopViewModel

class ProductsDetailsFragment : Fragment() {
    private val args = navArgs<ProductsDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductsDetailsBinding
    private lateinit var viewModel: ShopViewModel
    private lateinit var productsAdapter: ProductsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_products_details, container, false)
        viewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        val categoryId = args.value.categoryID
        when(categoryId){
            1 -> binding.categoryName.text = "Cloths"
            2 -> binding.categoryName.text = "Electronics"
            3 -> binding.categoryName.text = "Home"
            else -> binding.categoryName.text = "Shoes"
        }
        viewModel.getProducts(categoryId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRV()
        viewModel.ProductLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.progressBarProducts.isVisible = true
            } else {
                binding.progressBarProducts.isVisible = false
                productsAdapter.setData(it)
            }
        }
    }

    private fun initializeRV() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        productsAdapter = ProductsAdapter()
        binding.productsRecyclerView.apply {
            adapter = productsAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }
}