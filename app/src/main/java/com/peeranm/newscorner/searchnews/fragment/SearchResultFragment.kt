package com.peeranm.newscorner.searchnews.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peeranm.newscorner.R
import com.peeranm.newscorner.core.utils.collectWithLifecycle
import com.peeranm.newscorner.core.utils.setActionbarTitle
import com.peeranm.newscorner.databinding.FragmentSearchResultBinding
import com.peeranm.newscorner.newsfeed.model.Article
import com.peeranm.newscorner.newsfeed.presentation.adapter.ArticleAdapter
import com.peeranm.newscorner.newsfeed.presentation.adapter.ArticleLoadStateAdapter
import com.peeranm.newscorner.core.utils.OnItemClickListener
import com.peeranm.newscorner.searchnews.viewmodel.SearchNewsViewModel
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
            SearchResultFragmentDirections.actionSearchResultFragmentToArticleDetailsFragment(article = data)
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