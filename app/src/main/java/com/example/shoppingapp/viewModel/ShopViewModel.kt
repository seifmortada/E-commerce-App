package com.example.shoppingapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.database.MainDataBase
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.RandomProductByCategory
import com.example.shoppingapp.repository.ShopRepository
import com.example.shoppingapp.utils.Constants.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopViewModel(application: Application) : AndroidViewModel(application) {
    val repo: ShopRepository
    private val db: MainDataBase

    //Random Cloths product LiveData
    private val _clothsProductLiveData: MutableLiveData<List<RandomProductByCategory>> =
        MutableLiveData()

    val clothsProductLiveData: LiveData<List<RandomProductByCategory>> get() = _clothsProductLiveData

    //products LiveData for viewPager
    private val _ProductsLiveData: MutableLiveData<List<RandomProductByCategory>> =
        MutableLiveData()

    val ProductLiveData: LiveData<List<RandomProductByCategory>> get() = _ProductsLiveData

    //product details LiveData
    private val _ProductDetailsLiveData: MutableLiveData<List<RandomProductByCategory>> =
        MutableLiveData()

    val ProductDetailsLiveData: LiveData<List<RandomProductByCategory>> get() = _ProductDetailsLiveData

    // All Favourites
    private val _favouritesLiveData: MutableLiveData<List<RandomProductByCategory>> =
        MutableLiveData()

    val favouritesLiveData: LiveData<List<RandomProductByCategory>> get() = _favouritesLiveData

    // All Cart
    private val _cartLiveData: MutableLiveData<List<Cart>> =
        MutableLiveData()
    val cartLiveData: LiveData<List<Cart>> get() = _cartLiveData

    private val _totalPrice: MutableLiveData<Int> = MutableLiveData()
    val totalPrice: LiveData<Int> get() = _totalPrice

    init {
        db = MainDataBase.createDatabase(application.applicationContext)
        repo = ShopRepository(db.getDao())
//        getAllCart()
        getAllFav()
    }

    //Get cloths products for ViewPager
    fun getClothsProducts() = viewModelScope.launch {
        _clothsProductLiveData.value = repo.getClothsProduct()
    }

    //Get Products
    fun getProducts(categoryId: Int) = viewModelScope.launch {
        _ProductsLiveData.value = repo.getProducts(categoryId)
    }

    //Get product details
    fun getProductByTitle(productTitle: String) = viewModelScope.launch {
        _ProductDetailsLiveData.value = repo.getProductByTitle(productTitle)
    }

    fun insertFavIntoDb(product: RandomProductByCategory) = viewModelScope.launch {
        repo.insertFavIntoDb(product)
    }

    fun deleteFavFromDb(product: RandomProductByCategory) = viewModelScope.launch {
        repo.deleteFavFromDb(product)
    }

    fun getAllFav() {
        viewModelScope.launch {
            // Fetch favorites asynchronously
            try {
                repo.getAllFav().observeForever { favList ->
                    Log.d(TAG, "Data from repository: $favList")
                    _favouritesLiveData.postValue(favList)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data from repository", e)
            }
        }
    }

    fun insertCartIntoDb(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        repo.insertCartIntoDb(cart)
    }

    fun deleteSingleCartFromDb(cart: Cart) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteSingleCartFromDb(cart)
    }

    fun getAllCart() {
        viewModelScope.launch {
            // Fetch favorites asynchronously
            try {
                repo.getAllCart().observeForever { cartList ->
                    Log.d(TAG, "Data from repository: $cartList")
                    _cartLiveData.postValue(cartList)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data from repository", e)
            }
        }
    }

    fun deleteAllCart() = viewModelScope.launch (Dispatchers.IO){
        repo.deleteAllCart()
    }

    fun addTotalPrice(newPrice: Int) {
        _totalPrice.postValue(newPrice + 4)
    }
}
