package com.example.paging3.retrofit

import com.example.paging3.data.ProductList
import retrofit2.http.GET
import retrofit2.http.Query


interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ) : ProductList
}