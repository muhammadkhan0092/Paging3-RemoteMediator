package com.example.paging3.data


data class ProductList(
    val limit: Int=1,
    val products: List<Product> = emptyList<Product>(),
    val skip: Int=1,
    val total: Int=1
)