package com.example.paging3.loaders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3.databinding.ItemLoadingBinding

class LoadingAdapter : LoadStateAdapter<LoadingAdapter.LoadingViewHolder>() {
    override fun onBindViewHolder(
        holder: LoadingViewHolder,
        loadState: LoadState
    ) {
        holder.binding.progressBar.isVisible = loadState is LoadState.Loading
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingViewHolder {
        val view = ItemLoadingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadingViewHolder(view)
    }

    inner class LoadingViewHolder(val binding : ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)


}