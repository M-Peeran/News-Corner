package com.peeranm.newscorner.articledetails.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.peeranm.newscorner.R
import com.peeranm.newscorner.articledetails.viewmodel.ArticleViewModel
import com.peeranm.newscorner.core.utils.collectWithLifecycle
import com.peeranm.newscorner.core.utils.setActionbarTitle
import com.peeranm.newscorner.core.utils.showToast
import com.peeranm.newscorner.databinding.FragmentArticleDetailsBinding
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
        setActionbarTitle(R.string.article_headline, viewModel.articleFrom)
        binding.handleOnFabFavouriteClick()

        collectWithLifecycle(viewModel.message) { message ->
            if (message.isNotEmpty()) showToast(message)
        }

        collectWithLifecycle(viewModel.isFavourite) { isFavourite ->
            binding.toggleFabFavouriteArticleColor(isFavourite)
        }

        binding.loadArticle(viewModel.articleUrl)
    }

    private fun FragmentArticleDetailsBinding.loadArticle(url: String) {
        if (url.isNotEmpty()) {
            webviewArticle.webViewClient = WebViewClient()
            webviewArticle.loadUrl(url)
        }
    }

    private fun FragmentArticleDetailsBinding.toggleFabFavouriteArticleColor(isFavourite: Boolean = false) {
        fabFavouriteArticle.setColorFilter(
            if (isFavourite) {
                ContextCompat.getColor(requireContext(), R.color.yellow)
            } else TypedValue().let {
                requireContext().theme.resolveAttribute(R.attr.colorOnSecondary, it, true)
                it.data
            }
        )
    }

    private fun FragmentArticleDetailsBinding.handleOnFabFavouriteClick() {
        fabFavouriteArticle.setOnClickListener {
            viewModel.saveOrDeleteArticle()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}