package com.example.paging3.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3.data.Product
import com.example.paging3.retrofit.ProductApi

class ProductPagingSource(private val productApi: ProductApi) : PagingSource<Int, Product>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val skip = params.key ?: 0
            val response = productApi.getProducts(limit = 10, skip = skip)

            LoadResult.Page(
                data = response.products,
                prevKey = if (skip == 0) null else skip - 10,
                nextKey = if (skip + 10 >= response.total) null else skip + 10
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { position ->
            val closestPage = state.closestPageToPosition(position)
            closestPage?.prevKey?.plus(10)
                ?: closestPage?.nextKey?.minus(10)
        }
    }


}