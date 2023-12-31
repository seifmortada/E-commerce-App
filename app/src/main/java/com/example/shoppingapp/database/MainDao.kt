package com.example.shoppingapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.RandomProductByCategory

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(fav: RandomProductByCategory)

    @Delete
    suspend fun deleteFav(fav: RandomProductByCategory)

    @Query("SELECT * FROM product_table")
    fun getAllFav(): LiveData<List<RandomProductByCategory>>

    @Upsert
    suspend fun insertToCart(fav: Cart)

    @Delete
    suspend fun deleteSingleCart(cartItem: Cart)

    @Query("SELECT * FROM cart_table")
    fun getAllCart(): LiveData<List<Cart>>

    @Query("DELETE  FROM cart_table ")
    suspend fun deleteAllCart()
}