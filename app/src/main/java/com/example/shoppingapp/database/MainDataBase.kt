package com.example.shoppingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.RandomProductByCategory

@Database(
    entities = [RandomProductByCategory::class,Cart::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class MainDataBase : RoomDatabase() {
    abstract fun getDao(): MainDao

    companion object {
        @Volatile
        var INSTANCE: MainDataBase? = null

        fun createDatabase(context: Context): MainDataBase {
            var instance = INSTANCE
            if (instance != null)
                return instance
            instance = Room.databaseBuilder(context, MainDataBase::class.java, "shop_db")
                 .build()
            INSTANCE = instance
            return instance
        }
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new table for Cart
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `cart_table` " +
                            "(`category` TEXT NOT NULL, " +
                            "`creationAt` TEXT NOT NULL, " +
                            "`description` TEXT NOT NULL, " +
                            "`id` INTEGER NOT NULL, " +
                            "`images` TEXT NOT NULL, " +
                            "`price` INTEGER NOT NULL, " +
                            "`title` TEXT NOT NULL, " +
                            "`updatedAt` TEXT NOT NULL, " +
                            "PRIMARY KEY(`id`))"
                )
            }
        }
    }
}