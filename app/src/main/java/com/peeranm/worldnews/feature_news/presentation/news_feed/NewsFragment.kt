package com.peeranm.worldnews.feature_news.presentation.news_feed

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peeranm.worldnews.R
import com.peeranm.worldnews.core.*
import com.peeranm.worldnews.databinding.FragmentNewsBinding
import com.peeranm.worldnews.feature_news.model.Article
import com.peeranm.worldnews.feature_news.presentation.news_feed.news_country_dialog.CountryDialog
import com.peeranm.worldnews.feature_news.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(),
    OnItemClickListener<Article>,
    SearchView.OnQueryTextListener,
    AdapterView.OnItemClickListener,
    ArticleLoadStateAdapter.RetryListener {

    private val viewModel: NewsViewModel by viewModels()

    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding
    get() = _binding!!

    private var adapter: ArticleAdapter? = null
    private var articleLoadStateAdapter: ArticleLoadStateAdapter? = null
    private var categoryAdapter: ArrayAdapter<NewsCategory>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        handleOnBackPressed()
        setActionbarTitle(R.string.headlines)
        binding.bindList()

        collectWithLifecycle(viewModel.articles) {
            adapter?.submitData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bindNewsCategories()
    }

    override fun onRetry() {
        adapter?.retry()
    }

    private fun FragmentNewsBinding.bindList() {
        adapter = ArticleAdapter(this@NewsFragment)
        articleLoadStateAdapter = ArticleLoadStateAdapter(this@NewsFragment)
        listNews.adapter = adapter?.withLoadStateFooter(footer = articleLoadStateAdapter ?: return)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listNews.layoutManager = layoutManager
        listNews.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
    }

    private fun FragmentNewsBinding.bindNewsCategories() {
        categoryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            NewsCategory.values()
        )
        acTextNewsCategory.setAdapter(categoryAdapter)
        acTextNewsCategory.onItemClickListener = this@NewsFragment
    }

    override fun onItemClick(view: View?, data: Article, position: Int) {
        findNavController().navigate(
            NewsFragmentDirections.actionNewsFragmentToArticleFragment(article = data)
        )
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val category = categoryAdapter?.getItem(position)
        category?.let {
            adapter?.submitData(lifecycle, PagingData.empty())
            viewModel.setNewsCategory(it)
            viewModel.getTrendingNews()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val actionSearch = menu.findItem(R.id.actionSearch).actionView as SearchView
        actionSearch.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionChangeCountry -> CountryDialog().show(childFragmentManager, Constants.TAG_DIALOG_COUNTRY)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) {
            findNavController().navigate(
                NewsFragmentDirections.actionNewsFragmentToSearchResultFragment(query)
            )
        }
        return false
    }
    override fun onQueryTextChange(newText: String?) = false


    private fun setFragmentResultListener() {
        childFragmentManager.setFragmentResultListener(Constants.KEY_COUNTRY_DIALOG_RESULT_LISTENER, this) { requestKey, bundle ->
            if (requestKey == Constants.KEY_COUNTRY_DIALOG_RESULT_LISTENER) {
                val countryCode = bundle.get(Constants.ARG_COUNTRY_DIALOG_RESULT) as? CountryCode
                if (countryCode != null) {
                    adapter?.submitData(lifecycle, PagingData.empty())
                    viewModel.setCountryCode(countryCode)
                    viewModel.getTrendingNews()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter?.onClear()
        articleLoadStateAdapter?.onClear()
        articleLoadStateAdapter = null
        binding.acTextNewsCategory.onItemClickListener = null
        categoryAdapter = null
        adapter = null
        _binding = null
    }

}