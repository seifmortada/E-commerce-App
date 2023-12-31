package com.example.shoppingapp.adapters

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ItemFavouritesBinding
import com.example.shoppingapp.entity.RandomProductByCategory
import com.example.shoppingapp.viewModel.ShopViewModel
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class FavouriteProductsAdapter : RecyclerView.Adapter<FavouriteProductsAdapter.MyViewHolder>() {
    private var productsList = emptyList<RandomProductByCategory>()
    lateinit var viewModel: ShopViewModel
    lateinit var context: Context

    inner class MyViewHolder(val binding: ItemFavouritesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemFavouritesBinding.inflate(
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
            Picasso.get().load(currentProduct.images[0]).into(imgItemFav)
            favNameItemTxt.text = currentProduct.title
            favPrice.text = currentProduct.price.toString() + "$"
            deleteProduct.setOnClickListener {
                showDialog(currentProduct)
            }
        }
    }

    fun setData(list: List<RandomProductByCategory>) {
        productsList = list
        notifyDataSetChanged()
    }

    private fun showDialog(item: RandomProductByCategory) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Delete Confirmation")
        dialog.setMessage("Are you sure you want to delete this product")
        dialog.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteFavFromDb(item)
        }
        dialog.setNegativeButton("No") { _, _ -> }
        dialog.create()
        dialog.show()
    }
}

