package com.peeranm.newscorner.newsfeed.presentation.fragment

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
import com.peeranm.newscorner.R
import com.peeranm.newscorner.core.constants.Constants
import com.peeranm.newscorner.core.utils.*
import com.peeranm.newscorner.databinding.FragmentNewsBinding
import com.peeranm.newscorner.newsfeed.presentation.viewmodel.NewsViewModel
import com.peeranm.newscorner.newsfeed.model.Article
import com.peeranm.newscorner.newsfeed.presentation.adapter.ArticleAdapter
import com.peeranm.newscorner.newsfeed.presentation.adapter.ArticleLoadStateAdapter
import com.peeranm.newscorner.newsfeed.utils.CountryCode
import com.peeranm.newscorner.newsfeed.utils.NewsCategory
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

    private var selectedCategory: String = ""

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
        viewModel.initializeConnectionLiveData(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        handleOnBackPressed()
        setActionbarTitle(R.string.headlines)
        binding.bindList()
        observeConnectionState()

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

    private fun observeConnectionState() {
        viewModel.connectionLiveData.observe(viewLifecycleOwner) { isConnectionAvailable ->
            binding.toggleNoConnectionLayoutVisibility(!isConnectionAvailable)
            when {
                isConnectionAvailable && adapter!!.itemCount <= 0 -> viewModel.getTrendingNews()
            }
        }
    }

    private fun FragmentNewsBinding.toggleNoConnectionLayoutVisibility(showNow: Boolean = false) {
        noConnectionLayout.root.visibility = if (showNow) View.VISIBLE else View.GONE
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
        if (viewModel.connectionLiveData.value == false) {
            showToast(Constants.MESSAGE_NO_INTERNET_CONNECTION)
            return
        }
        findNavController().navigate(
            NewsFragmentDirections.actionNewsFragmentToArticleDetailsFragment(article = data)
        )
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (viewModel.connectionLiveData.value == false) {
            binding.acTextNewsCategory.setText(selectedCategory, false)
            showToast(Constants.MESSAGE_NO_INTERNET_CONNECTION)
            return
        }
        val category = categoryAdapter?.getItem(position)
        category?.let {
            adapter?.submitData(lifecycle, PagingData.empty())
            viewModel.setNewsCategory(it)
            viewModel.getTrendingNews()
            selectedCategory = category.name
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
        if (viewModel.connectionLiveData.value == false) {
            showToast(Constants.MESSAGE_NO_INTERNET_CONNECTION)
            return false
        }
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
            when (requestKey) {
                Constants.KEY_COUNTRY_DIALOG_RESULT_LISTENER -> {
                    if (viewModel.connectionLiveData.value == true) {
                        val countryCode = bundle.get(Constants.ARG_COUNTRY_DIALOG_RESULT) as? CountryCode
                        if (countryCode != null) {
                            adapter?.submitData(lifecycle, PagingData.empty())
                            viewModel.setCountryCode(countryCode)
                            viewModel.getTrendingNews()
                        }
                    } else showToast(Constants.MESSAGE_NO_INTERNET_CONNECTION)
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