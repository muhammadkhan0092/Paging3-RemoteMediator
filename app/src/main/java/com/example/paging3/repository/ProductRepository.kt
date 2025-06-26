package com.example.paging3.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.RemoteMediator
import com.example.paging3.db.ProductDatabase
import com.example.paging3.paging.ProductPagingSource
import com.example.paging3.retrofit.ProductApi
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi: ProductApi,private val productDatabase: ProductDatabase) {
    @OptIn(ExperimentalPagingApi::class)
    fun getProducts() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100
        ),
        remoteMediator = com.example.paging3.mediator.RemoteMediator(productApi,productDatabase),
        pagingSourceFactory = {
            android.util.Log.d("Repository", "Creating PagingSource")
            productDatabase.getProductDao().getProducts()
        }
    ).flow
}
