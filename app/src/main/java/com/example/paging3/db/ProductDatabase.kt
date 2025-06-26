package com.example.paging3.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging3.data.Product
import com.example.paging3.data.ProductList
import com.example.paging3.data.RemoteKey

@Database([RemoteKey::class, Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getRemoteDao() : RemotekeyDao
    abstract fun getProductDao() : ProductDao
}