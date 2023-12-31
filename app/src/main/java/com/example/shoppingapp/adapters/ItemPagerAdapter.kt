package com.example.shoppingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ItemPagerBinding
import com.example.shoppingapp.entity.RandomProductByCategory
import com.squareup.picasso.Picasso
import okhttp3.internal.notify

class ItemPagerAdapter : RecyclerView.Adapter<ItemPagerAdapter.MyViewHolder>() {
    private var productList = emptyList<RandomProductByCategory>()

    inner class MyViewHolder(val binding: ItemPagerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.binding.apply {
            Picasso.get().load(currentProduct.images[0]).into(imgItem2)
        }
    }

    fun setData(list: List<RandomProductByCategory>) {
        productList = list
        notifyDataSetChanged()
    }
}

