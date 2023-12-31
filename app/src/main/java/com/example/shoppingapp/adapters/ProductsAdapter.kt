package com.example.shoppingapp.adapters

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ItemCategoryBinding
import com.example.shoppingapp.databinding.ItemPagerBinding
import com.example.shoppingapp.databinding.ItemProductDetailsBinding
import com.example.shoppingapp.entity.Category
import com.example.shoppingapp.entity.RandomProductByCategory
import com.example.shoppingapp.ui.MainFragmentDirections
import com.example.shoppingapp.ui.ProductsDetailsFragment
import com.example.shoppingapp.ui.ProductsDetailsFragmentDirections
import com.squareup.picasso.Picasso
import okhttp3.internal.notify

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {
    private var productsList = emptyList<RandomProductByCategory>()

    inner class MyViewHolder(val binding: ItemProductDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemProductDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = productsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProduct = productsList[position]
        holder.binding.apply {
            Picasso.get().load(currentProduct.images[0]).into(productImg)
            productName.text = currentProduct.title
            productPrice.text = currentProduct.price.toString() + "$"
            productsLayout.setOnClickListener {
                val action =
                    ProductsDetailsFragmentDirections.actionProductsDetailsFragmentToProductFragment(
                        currentProduct.title
                    )
                it.findNavController().navigate(action)
            }

        }
    }

    fun setData(list: List<RandomProductByCategory>) {
        productsList = list
        notifyDataSetChanged()
    }
}

