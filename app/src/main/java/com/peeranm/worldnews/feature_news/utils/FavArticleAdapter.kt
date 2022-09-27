package com.peeranm.worldnews.feature_news.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.peeranm.worldnews.databinding.ArticleItemBinding
import com.peeranm.worldnews.feature_news.data.local.entity.FavArticle

class FavArticleAdapter(listener: OnItemClickListener<FavArticle>)
    : RecyclerView.Adapter<FavArticleAdapter.FavArticleHolder>() {

    private var listener: OnItemClickListener<FavArticle>? = null

    init { this.listener = listener }

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<FavArticle>() {
        override fun areItemsTheSame(oldItem: FavArticle, newItem: FavArticle): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: FavArticle, newItem: FavArticle): Boolean {
            return oldItem == newItem
        }
    })

    inner class FavArticleHolder(private val binding: ArticleItemBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init { binding.root.setOnClickListener(this) }

        fun bind(favArticle: FavArticle) {
            binding.apply {
                textTitle.text = favArticle.title
                textDescription.text = favArticle.description
                textSource.text = favArticle.source
                textPublishedAt.text = favArticle.publishedAt
                imageArticleImage.load(favArticle.urlToImage)
            }
        }

        override fun onClick(view: View?) {
            val favArticle = dataDiffer.currentList[bindingAdapterPosition] ?: return
            listener?.onItemClick(view, favArticle, bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavArticleHolder {
        return FavArticleHolder(
            ArticleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavArticleHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position] ?: return)
    }

    override fun getItemCount() = dataDiffer.currentList.size

    fun submitList(data: List<FavArticle>) {
        dataDiffer.submitList(data)
    }

    fun onClear() {
        this.listener = null
    }
}