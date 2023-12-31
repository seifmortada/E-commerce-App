package com.example.shoppingapp.adapters

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ItemCategoryBinding
import com.example.shoppingapp.entity.Category
import com.example.shoppingapp.ui.MainFragmentDirections
import com.example.shoppingapp.ui.ProductsDetailsFragment
import com.squareup.picasso.Picasso
import okhttp3.internal.notify

class ItemCategoryAdapter : RecyclerView.Adapter<ItemCategoryAdapter.MyViewHolder>() {
    private var categoryList = emptyList<Category>()

    inner class MyViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCategory = categoryList[position]
        holder.binding.apply {
            Picasso.get().load(currentCategory.image).into(imgItem)
            categoryNameItemTxt.text = currentCategory.name
            categoryItem.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToProductsDetailsFragment(
                    currentCategory.id
                )
                it.findNavController().navigate(action)
            }
        }
    }

    fun setData(list: List<Category>) {
        categoryList = list
        notifyDataSetChanged()
    }
}

