package com.example.shoppingapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.shoppingapp.database.Converter

@Entity("product_table")
@TypeConverters(Converter::class)
data class RandomProductByCategory(
    val category: Category,
    val creationAt: String,
    val description: String,
    @PrimaryKey
    val id: Int,
    val images: List<String>,
    val price: Int,
    val title: String,
    val updatedAt: String
)