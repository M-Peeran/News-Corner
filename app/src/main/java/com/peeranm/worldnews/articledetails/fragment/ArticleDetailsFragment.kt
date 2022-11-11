package com.peeranm.worldnews.articledetails.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.peeranm.worldnews.core.utils.collectWithLifecycle
import com.peeranm.worldnews.core.utils.showToast
import com.peeranm.worldnews.articledetails.viewmodel.ArticleViewModel
import com.peeranm.worldnews.databinding.FragmentArticleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding: FragmentArticleDetailsBinding
    get() = _binding!!

    private val viewModel: ArticleViewModel by viewModels()
    private val args: ArticleDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loadArticle()
        binding.handleOnFabFavouriteClick()

        collectWithLifecycle(viewModel.message) { message -> showToast(message) }
    }

    private fun FragmentArticleDetailsBinding.toggleFabFavouriteArticle(hideNow: Boolean = false) {
        fabFavouriteArticle.visibility = if (hideNow) View.GONE else View.VISIBLE
    }

    private fun FragmentArticleDetailsBinding.loadArticle() {
        val article = args.article
        val articleUrl = args.articleUrl
        val isFavouriteArticle = args.isFavourite

        when {
            article != null -> {
                webviewArticle.webViewClient = WebViewClient()
                webviewArticle.loadUrl(article.url)
                toggleFabFavouriteArticle(hideNow = false)
            }
            articleUrl.isNullOrEmpty() -> {
                showToast("Article url is not found!")
                toggleFabFavouriteArticle(hideNow = true)
            }
            isFavouriteArticle -> {
                webviewArticle.webViewClient = WebViewClient()
                webviewArticle.loadUrl(articleUrl)
                toggleFabFavouriteArticle(hideNow = true)
            }
        }
    }

    private fun FragmentArticleDetailsBinding.handleOnFabFavouriteClick() {
        fabFavouriteArticle.setOnClickListener {
            binding.toggleFabFavouriteArticle(true)
            viewModel.saveArticle()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}