package com.example.shoppingapp.api

import com.example.shoppingapp.entity.RandomProductByCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopAPI {
    //Get Cloths products for the ViewPager
    @GET("products/?categoryId=1")
    suspend fun getClothsProducts(): Response<List<RandomProductByCategory>>

    @GET("products/")
    suspend fun getProducts(
        @Query("categoryId")
        categoryId: Int
    ): Response<List<RandomProductByCategory>>

    //Get
    @GET("products/")
    suspend fun getProductByTitle(
        @Query("title")
        productTitle: String
    ): Response<List<RandomProductByCategory>>
}