package com.example.shoppingapp.adapters

import android.app.AlertDialog
import android.content.ClipData.Item
import android.content.Context
import android.icu.number.IntegerWidth
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.ItemCartBinding
import com.example.shoppingapp.databinding.ItemCategoryBinding
import com.example.shoppingapp.databinding.ItemPagerBinding
import com.example.shoppingapp.databinding.ItemProductDetailsBinding
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.Category
import com.example.shoppingapp.entity.RandomProductByCategory
import com.example.shoppingapp.ui.MainFragmentDirections
import com.example.shoppingapp.ui.ProductsDetailsFragment
import com.example.shoppingapp.ui.ProductsDetailsFragmentDirections
import com.example.shoppingapp.viewModel.ShopViewModel
import com.squareup.picasso.Picasso
import okhttp3.internal.notify

class CartAdapter : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    private var cartList = emptyList<Cart>()
    lateinit var viewModel : ShopViewModel
    lateinit var context: Context
    inner class MyViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProduct = cartList[position]
        holder.binding.apply {
            Picasso.get().load(currentProduct.images[0]).into(productImg)
            productName.text = currentProduct.title
            productPrice.text = currentProduct.price.toString() + "$"
            addItemBtn.setOnClickListener {
                val oldPrice = currentProduct.price
                itemNumberAdded.text = Integer.parseInt(itemNumberAdded.text.toString()).plus(1).toString()
                productPrice.text = currentProduct.price.plus(oldPrice).toString()
                viewModel.addTotalPrice(Integer.parseInt(productPrice.text.toString()))
            }
            minusItemBtn.setOnClickListener {
                val oldPrice = currentProduct.price
                if (Integer.parseInt(itemNumberAdded.text.toString()) == 0){
                    showDialogToDelete(currentProduct)
                    return@setOnClickListener
                }
                itemNumberAdded.text = Integer.parseInt(itemNumberAdded.text.toString()).minus(1).toString()
                productPrice.text = currentProduct.price.minus(oldPrice).toString()
            }
        }
    }

    private fun showDialogToDelete(cartItem:Cart) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Delete Confirmation")
        dialog.setMessage("Are you sure you want to delete this product from cart")
        dialog.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteSingleCartFromDb(cartItem)
        }
        dialog.setNegativeButton("No") { _, _ -> }
        dialog.create()
        dialog.show()
    }

    fun setData(list: List<Cart>) {
        cartList = list
        notifyDataSetChanged()
    }
}

