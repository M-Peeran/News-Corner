package com.peeranm.worldnews.feature_news.presentation.favourite_articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peeranm.worldnews.core.collectWithLifecycle
import com.peeranm.worldnews.core.handleOnBackPressed
import com.peeranm.worldnews.databinding.FragmentFavouriteArticlesBinding
import com.peeranm.worldnews.feature_news.data.local.entity.FavArticle
import com.peeranm.worldnews.feature_news.utils.FavArticleAdapter
import com.peeranm.worldnews.feature_news.utils.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteArticlesFragment : Fragment(), OnItemClickListener<FavArticle> {

    private var _binding: FragmentFavouriteArticlesBinding? = null
    private val binding: FragmentFavouriteArticlesBinding
    get() = _binding!!

    private val viewModel: FavouriteArticlesViewModel by viewModels()

    private var adapter: FavArticleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteArticlesBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPressed()
        binding.bindList()

        collectWithLifecycle(viewModel.favArticles) {
            adapter?.submitList(it)
        }

    }

    private fun FragmentFavouriteArticlesBinding.bindList() {
        adapter = FavArticleAdapter(this@FavouriteArticlesFragment)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listFavouriteArticles.adapter = adapter
        listFavouriteArticles.layoutManager = layoutManager
        listFavouriteArticles.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
    }

    override fun onItemClick(view: View?, data: FavArticle, position: Int) {
        findNavController().navigate(
            FavouriteArticlesFragmentDirections.actionFavouriteArticlesFragmentToArticleFragment(
                articleUrl = data.url,
                isFavourite = true
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter?.onClear()
        adapter = null
        _binding = null
    }
}