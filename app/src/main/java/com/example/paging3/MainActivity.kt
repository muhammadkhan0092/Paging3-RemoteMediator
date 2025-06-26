package com.example.paging3

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paging3.databinding.ActivityMainBinding
import com.example.paging3.loaders.LoadingAdapter
import com.example.paging3.paging.ProductPagingAdapter
import com.example.paging3.vm.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: ProductPagingAdapter
    val viewModel : ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        observeList()
    }

    private fun setupAdapter() {
        adapter = ProductPagingAdapter()
        binding.quoteRv.layoutManager = LinearLayoutManager(this)
        binding.quoteRv.setHasFixedSize(true)
        binding.quoteRv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadingAdapter(),
            footer = LoadingAdapter()
        )
    }

    private fun observeList() {
        lifecycleScope.launch {
            viewModel.list.collectLatest { pagingData ->
                Log.d("MainActivity", "Submitting data to adapter")
                adapter.submitData(pagingData)
            }
        }
    }

}