package com.example.shoppingapp.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.shoppingapp.R
import com.example.shoppingapp.adapters.ItemCategoryAdapter
import com.example.shoppingapp.adapters.ItemPagerAdapter
import com.example.shoppingapp.databinding.FragmentMainBinding
import com.example.shoppingapp.databinding.ItemCategoryBinding
import com.example.shoppingapp.entity.Category
import com.example.shoppingapp.viewModel.ShopViewModel
import com.squareup.picasso.Picasso
import me.relex.circleindicator.CircleIndicator3
import okhttp3.internal.wait
import java.util.Timer
import java.util.TimerTask

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: ShopViewModel
    private lateinit var categoryAdapter: ItemCategoryAdapter
    private lateinit var indicatorAdapter: ItemPagerAdapter
    private lateinit var autoScrollRunnable :Runnable
    @Suppress("DEPRECATION")
    val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        viewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        viewModel.getClothsProducts()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRV()

        // Define a Runnable to handle automatic scrolling
         autoScrollRunnable = object : Runnable {
            override fun run() {
                // Check if the ViewPager has more than 2 items
                if (categoryAdapter.itemCount > 2) {
                    // Get the current item index
                    val currentItem = binding.myRecyclerViewPager.currentItem

                    // Calculate the index of the next item, considering a circular behavior
                    val nextItem = (currentItem + 1) % categoryAdapter.itemCount
                    // Scroll to the next item
                    binding.myRecyclerViewPager.setCurrentItem(nextItem, true)
                }

                // Post the same runnable with a delay of 4 seconds
                handler.postDelayed(this, 4000)
            }
        }

        //Observing the cloths products for the ViewPager
        viewModel.clothsProductLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.progressBarPager.visibility = View.VISIBLE
            } else {
                binding.progressBarPager.visibility = View.INVISIBLE
                indicatorAdapter.setData(it)
                if (categoryAdapter.itemCount > 1)
                    handler.postDelayed(autoScrollRunnable, 3000)
            }
        }
    }

    private fun initializeRV() {
        binding.apply {
            indicatorAdapter = ItemPagerAdapter()
            myRecyclerViewPager.adapter = indicatorAdapter
            myRecyclerViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            val myIndicator: CircleIndicator3 = indicator
            myIndicator.setViewPager(myRecyclerViewPager)
        }
        val cloths = Category(
            "",
            1,
            "https://i.imgur.com/7eW9nXP.jpeg",
            "Cloths",
            ""
        )
        val home = Category("", 3, "https://i.imgur.com/JktHE1C.jpeg", "Home", "")
        val electronics = Category("", 2, "https://i.imgur.com/Ro5z6Tn.jpeg", "Electronics", "")
        val shoes = Category(
            "",
            4,
            "https://i.imgur.com/Jf9DL9R.jpeg%22https://i.imgur.com/62gGzeF.jpeg",
            "Shoes",
            ""
        )
        val listCategories = listOf<Category>(cloths, home, electronics, shoes)
        categoryAdapter = ItemCategoryAdapter()
        categoryAdapter.setData(listCategories)
        binding.recyclerViewCategories.apply {
            adapter = categoryAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable)
    }
}