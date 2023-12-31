package com.example.shoppingapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.shoppingapp.api.RetrofitInstance
import com.example.shoppingapp.database.MainDao
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.RandomProductByCategory
import com.example.shoppingapp.utils.Constants.TAG

class ShopRepository(private val dao: MainDao) {
    //Get Cloths Product
    suspend fun getClothsProduct(): List<RandomProductByCategory>? {
        val response = RetrofitInstance.api.getClothsProducts()
        if (response.isSuccessful && response.body() != null) {
            Log.e(TAG, "Cloths : are SUCCSUFULL")
            response.body()?.let {
                return it
            }
        }
        return null
    }

    //Get Products
    suspend fun getProducts(categoryId: Int): List<RandomProductByCategory>? {
        val response = RetrofitInstance.api.getProducts(categoryId)
        if (response.isSuccessful && response.body() != null) {
            Log.e(TAG, "Products : are successful")
            response.body()?.let {
                return it
            }
        }
        return null
    }

    //Get product by title
    suspend fun getProductByTitle(productTitle: String): List<RandomProductByCategory>? {
        Log.e(TAG, "Products IS $productTitle")
        val response = RetrofitInstance.api.getProductByTitle(productTitle)
        if (response.isSuccessful && response.body() != null) {
            Log.e(TAG, "Products TITLE RIGHT")
            response.body()?.let {
                return it
            }
        } else {
            Log.e(TAG, response.errorBody().toString())
        }
        return null
    }

    //Inert Into database Fav
    suspend fun insertFavIntoDb(product: RandomProductByCategory) {
        dao.insertFav(product)
        Log.d(TAG, "Product inserted into database: ${product.title}")
    }

    //delete from database Fav
    suspend fun deleteFavFromDb(product: RandomProductByCategory) {
        dao.deleteFav(product)
        Log.d(TAG, "Product deleted from database: ${product.title}")
    }

    fun getAllFav(): LiveData<List<RandomProductByCategory>> {
        return dao.getAllFav()
    }

    //Inert Into database Fav
    suspend fun insertCartIntoDb(cart: Cart) {
        dao.insertToCart(cart)
        Log.e(TAG, "cart inserted into database: ${cart.title}")
    }

    //delete from database Fav
    suspend fun deleteSingleCartFromDb(cart: Cart) {
        dao.deleteSingleCart(cart)
        Log.e(TAG, "Cart deleted from database: ${cart.title}")
    }

    fun getAllCart(): LiveData<List<Cart>> {
        return dao.getAllCart()
    }

    suspend fun deleteAllCart() {
        dao.deleteAllCart()
        Log.e(TAG, "All Cart deleted from database")

    }
}