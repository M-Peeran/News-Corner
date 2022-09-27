package com.peeranm.worldnews.feature_news.presentation.search_result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peeranm.worldnews.R
import com.peeranm.worldnews.core.collectWithLifecycle
import com.peeranm.worldnews.core.setActionbarTitle
import com.peeranm.worldnews.databinding.FragmentSearchResultBinding
import com.peeranm.worldnews.feature_news.model.Article
import com.peeranm.worldnews.feature_news.utils.ArticleAdapter
import com.peeranm.worldnews.feature_news.utils.ArticleLoadStateAdapter
import com.peeranm.worldnews.feature_news.utils.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment(), OnItemClickListener<Article>, ArticleLoadStateAdapter.RetryListener {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding: FragmentSearchResultBinding
    get() = _binding!!

    private val viewModel: SearchNewsViewModel by viewModels()

    private var adapter: ArticleAdapter? = null
    private var articleLoadStateAdapter: ArticleLoadStateAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionbarTitle(R.string.search_results)
        binding.bindList()

        collectWithLifecycle(viewModel.searchResults) {
            adapter?.submitData(it)
        }
    }

    override fun onItemClick(view: View?, data: Article, position: Int) {
        findNavController().navigate(
            SearchResultFragmentDirections.actionSearchResultFragmentToArticleFragment(article = data)
        )
    }

    override fun onRetry() {
        adapter?.retry()
    }

    private fun FragmentSearchResultBinding.bindList() {
        adapter = ArticleAdapter(this@SearchResultFragment)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        articleLoadStateAdapter = ArticleLoadStateAdapter(this@SearchResultFragment)
        listSearchResults.adapter = adapter?.withLoadStateFooter(footer = articleLoadStateAdapter ?: return)
        listSearchResults.layoutManager = layoutManager
        listSearchResults.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        articleLoadStateAdapter?.onClear()
        adapter?.onClear()
        articleLoadStateAdapter = null
        adapter = null
        _binding = null
    }

}