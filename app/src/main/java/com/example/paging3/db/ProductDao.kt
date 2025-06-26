package com.example.paging3.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.paging3.data.Product


@Dao
interface ProductDao {
    @Insert
    suspend fun insertProducts(product: List<Product>)

    @Query("DELETE FROM Product")
    suspend fun deleteProduct()

    @Query("SELECT * FROM Product")
    fun getProducts(): PagingSource<Int, Product>

}