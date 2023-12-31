package com.example.shoppingapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.adapters.FavouriteProductsAdapter
import com.example.shoppingapp.adapters.ItemCategoryAdapter
import com.example.shoppingapp.databinding.FragmentFavoritesBinding
import com.example.shoppingapp.databinding.FragmentMainBinding
import com.example.shoppingapp.databinding.ItemCategoryBinding
import com.example.shoppingapp.utils.Constants.TAG
import com.example.shoppingapp.viewModel.ShopViewModel

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: ShopViewModel
    private lateinit var favouriteProductsAdapter: FavouriteProductsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        viewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        initilaizeRv()
        favouriteProductsAdapter.viewModel = viewModel
        favouriteProductsAdapter.context = requireContext()
        viewModel.getAllFav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favouritesLiveData.observe(viewLifecycleOwner) { favList ->
            if (favList.isEmpty()) {
                binding.emptyImg.visibility = View.VISIBLE
            } else {
                binding.emptyImg.visibility = View.GONE
                favouriteProductsAdapter.setData(favList)
            }
        }
    }

    private fun initilaizeRv() {
        favouriteProductsAdapter = FavouriteProductsAdapter()
        favouriteProductsAdapter.viewModel = viewModel
        binding.favRV.apply {
            adapter = favouriteProductsAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }


}