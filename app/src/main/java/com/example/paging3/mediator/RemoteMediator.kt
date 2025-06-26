package com.example.paging3.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging3.data.Product
import com.example.paging3.data.RemoteKey
import com.example.paging3.db.ProductDatabase
import com.example.paging3.retrofit.ProductApi

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val productApi: ProductApi,
    private val productDatabase: ProductDatabase
) : RemoteMediator<Int, Product>() {
    private val productDao = productDatabase.getProductDao()
    private val remoteKeyDao = productDatabase.getRemoteDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Product>
    ): MediatorResult {
        val offset = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyForClosestToCurrentPosition(state)
                remoteKeys?.next?.minus(1) ?: 0
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prev
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                prevPage
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.next
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                nextPage
            }
        }
        try {
            val response = productApi.getProducts(limit = state.config.pageSize, skip = offset)
            val products = response.products
            val endOfPagination = response.skip + products.size >= response.total

            val prevKey = if (offset == 0) null else offset - state.config.pageSize
            val nextKey = if (endOfPagination) null else offset + products.size

            productDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteRemoteKeys()
                    productDao.deleteProduct()
                }

                val keys = products.map { product ->
                    RemoteKey(
                        id = product.id,
                        prev = prevKey,
                        next = nextKey
                    )
                }
                remoteKeyDao.addRemoteKeys(keys)
                productDao.insertProducts(products)
            }

            return MediatorResult.Success(endOfPagination)

        } catch (e: Exception) {
           return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForClosestToCurrentPosition(
        state: PagingState<Int, Product>
    ): RemoteKey? {
        val position = state.anchorPosition
        if (position != null) {
            val closestItem = state.closestItemToPosition(position)
            if (closestItem != null) {
                return remoteKeyDao.getRemoteKey(closestItem.id)
            }
        }
        return null
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Product>
    ): RemoteKey? {
        val lastPage = state.pages.lastOrNull { it.data.isNotEmpty() }
        if (lastPage != null) {
            val lastItem = lastPage.data.lastOrNull()
            if (lastItem != null) {
                return remoteKeyDao.getRemoteKey(lastItem.id)
            }
        }
        return null
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Product>
    ): RemoteKey? {
        val firstPage = state.pages.firstOrNull { it.data.isNotEmpty() }
        if (firstPage != null) {
            val firstItem = firstPage.data.firstOrNull()
            if (firstItem != null) {
                return remoteKeyDao.getRemoteKey(firstItem.id)
            }
        }
        return null
    }

}