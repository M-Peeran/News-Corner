package com.peeranm.newscorner.favouritearticles.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peeranm.newscorner.R
import com.peeranm.newscorner.core.constants.Constants
import com.peeranm.newscorner.core.utils.*
import com.peeranm.newscorner.databinding.FragmentFavouriteArticlesBinding
import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.favouritearticles.presentation.viewmodel.FavouriteArticlesViewModel
import com.peeranm.newscorner.favouritearticles.presentation.adapter.FavArticleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteArticlesFragment : Fragment(), OnItemClickListener<FavArticle> {

    private var _binding: FragmentFavouriteArticlesBinding? = null
    private val binding: FragmentFavouriteArticlesBinding
    get() = _binding!!

    private val viewModel: FavouriteArticlesViewModel by viewModels()

    private var adapter: FavArticleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializeConnectionLiveData(requireContext())
    }

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
        setActionbarTitle(R.string.favourite_articles)
        handleOnBackPressed()
        binding.bindList()
        observeConnectionState()

        collectWithLifecycle(viewModel.favArticles) {
            binding.toggleNoArticlesFoundMessageVisibility(it.isEmpty())
            adapter?.submitList(it)
        }

    }

    private fun observeConnectionState() {
        viewModel.connectionLiveData.observe(viewLifecycleOwner) { isConnectionAvailable ->
            binding.toggleNoConnectionLayoutVisibility(!isConnectionAvailable)
        }
    }

    private fun FragmentFavouriteArticlesBinding.toggleNoArticlesFoundMessageVisibility(showNow: Boolean = true) {
        if (showNow) {
            listFavouriteArticles.visibility = View.GONE
            textNoArticlesFound.visibility = View.VISIBLE
        } else {
            textNoArticlesFound.visibility = View.GONE
            listFavouriteArticles.visibility = View.VISIBLE
        }
    }

    private fun FragmentFavouriteArticlesBinding.toggleNoConnectionLayoutVisibility(showNow: Boolean = false) {
        noConnectionLayout.root.visibility = if (showNow) View.VISIBLE else View.GONE
    }

    private fun FragmentFavouriteArticlesBinding.bindList() {
        adapter = FavArticleAdapter(this@FavouriteArticlesFragment)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listFavouriteArticles.adapter = adapter
        listFavouriteArticles.layoutManager = layoutManager
        listFavouriteArticles.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
    }

    override fun onItemClick(view: View?, data: FavArticle, position: Int) {
        if (viewModel.connectionLiveData.value == false) {
            showToast(Constants.MESSAGE_NO_INTERNET_CONNECTION)
            return
        }
        findNavController().navigate(
            FavouriteArticlesFragmentDirections.actionFavouriteArticlesFragmentToArticleDetailsFragment(
                articleUrl = data.url,
                source = data.source,
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