package com.peeranm.worldnews.feature_news.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.peeranm.worldnews.databinding.ArticleItemBinding
import com.peeranm.worldnews.feature_news.model.Article



class ArticleAdapter(
    listener: OnItemClickListener<Article>
) : PagingDataAdapter<Article, ArticleAdapter.ArticleHolder>(ArticleDiffCallback()) {

    private var listener: OnItemClickListener<Article>? = null

    init { this.listener = listener }

    inner class ArticleHolder(private val binding: ArticleItemBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init { binding.root.setOnClickListener(this) }

        fun bind(article: Article) {
            binding.apply {
                textTitle.text = article.title
                textDescription.text = article.description
                textSource.text = article.source
                textPublishedAt.text = article.publishedAt
                imageArticleImage.load(article.urlToImage)
            }
        }

        override fun onClick(view: View?) {
            val article = getItem(bindingAdapterPosition) ?: return
            listener?.onItemClick(view, article, bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        return ArticleHolder(
            ArticleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    fun onClear() {
        this.listener = null
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}