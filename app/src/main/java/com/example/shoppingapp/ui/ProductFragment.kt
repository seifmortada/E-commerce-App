package com.example.shoppingapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.adapters.ProductsAdapter
import com.example.shoppingapp.databinding.FragmentProductBinding
import com.example.shoppingapp.databinding.FragmentProductsDetailsBinding
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.RandomProductByCategory
import com.example.shoppingapp.viewModel.ShopViewModel
import com.squareup.picasso.Picasso

class ProductFragment : Fragment() {
    private val args = navArgs<ProductFragmentArgs>()
    private lateinit var binding: FragmentProductBinding
    private lateinit var viewModel: ShopViewModel
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var product: RandomProductByCategory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        viewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        val productName = args.value.productTitle
        viewModel.getProductByTitle(productName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.ProductDetailsLiveData.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.progressBarProducts.isVisible = true
            } else {
                binding.progressBarProducts.isVisible = false
                product = it[0]
                binding.apply {
                    productName.text = product.category.name
                    productTitle.text = product.title
                    productPrice.text = product.price.toString() + "$"
                    Picasso.get().load(product.images[0]).into(productImage)
                    favouriteBtn.setOnClickListener {
                        viewModel.insertFavIntoDb(product)
                        Toast.makeText(requireContext(), "Added to Favourites", Toast.LENGTH_SHORT)
                            .show()
                    }
                    cartBtn.setOnClickListener {
                        castTheProductToCartThenAddIt()
                    }

                }
            }
        }
        binding.cardViewProduct.setBackgroundResource(R.drawable.spinner_background)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinner_items,
            R.layout.custom_spinner_item
        ).also { adapter ->
            binding.spinnerSizes.adapter = adapter
        }
    }

    private fun castTheProductToCartThenAddIt() {
        val cartItem = Cart(
            product.category,
            product.creationAt,
            product.description,
            product.id,
            product.images,
            product.price,
            product.title,
            product.updatedAt
        )
        viewModel.insertCartIntoDb(cartItem)
        Toast.makeText(requireContext(), "Added To Cart", Toast.LENGTH_SHORT).show()
    }
}