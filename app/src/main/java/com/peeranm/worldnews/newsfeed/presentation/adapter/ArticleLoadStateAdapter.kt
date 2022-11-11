package com.peeranm.worldnews.newsfeed.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peeranm.worldnews.databinding.PagingLoadingItemBinding

class ArticleLoadStateAdapter(retryListener: RetryListener)
    : LoadStateAdapter<ArticleLoadStateAdapter.ArticlesLoadStateViewHolder>() {

    private var retryListener: RetryListener? = null

    init { this.retryListener = retryListener }

    inner class ArticlesLoadStateViewHolder(
        private val binding: PagingLoadingItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) binding.textError.text = loadState.error.localizedMessage
            binding.progressbar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
            binding.buttonRetry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            binding.textError.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            binding.buttonRetry.setOnClickListener { retryListener?.onRetry() }
        }
    }

    override fun onBindViewHolder(
        holder: ArticlesLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = ArticlesLoadStateViewHolder(
        PagingLoadingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    fun onClear() {
        this.retryListener = null
    }

    interface RetryListener {
        fun onRetry()
    }
}