package com.example.shoppingapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.adapters.CartAdapter
import com.example.shoppingapp.adapters.ItemCategoryAdapter
import com.example.shoppingapp.databinding.FragmentCartBinding
import com.example.shoppingapp.databinding.FragmentMainBinding
import com.example.shoppingapp.databinding.ItemCategoryBinding
import com.example.shoppingapp.utils.Constants.TAG
import com.example.shoppingapp.viewModel.ShopViewModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: ShopViewModel
    private lateinit var cartAdapter: CartAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        viewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        cartAdapter = CartAdapter()
        cartAdapter.context = requireContext()
        cartAdapter.viewModel = viewModel
//        viewModel.getAllCart()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRv()
        val repo = viewModel.repo
        repo.getAllCart().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.apply {
                    emptyImgCart.isVisible=true
                    emptyTxt.isVisible=true
                    buyBtn.setOnClickListener {
                        Toast.makeText(requireContext(), "Cart is empty :(", Toast.LENGTH_SHORT)
                            .show()
                    }
                    binding.invalidateAll()
                }
            } else {
                cartAdapter.setData(it)
                Log.e(TAG, "First Item ")
                binding.apply {
                    emptyImgCart.isVisible=false
                    emptyTxt.isVisible=false
                    itemsNumber.text = it.size.toString() + " items"
                    deleteCart.setOnClickListener {
                        showDeleteDialog()
                        return@setOnClickListener
                    }
                    buyBtn.setOnClickListener {
                        Toast.makeText(
                            requireContext(),
                            "Order is on the way :)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                invalidateAll()
                }
            }
        }
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            if (it > 0) {
                binding.totalPrice.text = "$ $it"
            }
        }
    }

    private fun showDeleteDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Delete Confirmation")
        dialog.setMessage("Are you sure you want to delete All products from cart")
        dialog.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllCart()
        }
        dialog.setNegativeButton("No") { _, _ -> }
        dialog.create()
        dialog.show()
    }

    private fun initializeRv() {
        binding.rvCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}