package com.example.paging3.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3.data.Product
import com.example.paging3.databinding.ItemQuoteLayoutBinding

class ProductPagingAdapter :
    PagingDataAdapter<Product, ProductPagingAdapter.QuoteViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuoteViewHolder {
        val view = ItemQuoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: QuoteViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        Log.d("khan","binding ${item}")
        holder.binding.quote.text = item?.description ?: "No content"
    }

    inner class QuoteViewHolder(val binding: ItemQuoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}
