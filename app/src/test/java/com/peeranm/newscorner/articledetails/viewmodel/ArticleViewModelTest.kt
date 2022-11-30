package com.peeranm.newscorner.articledetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.peeranm.newscorner.articledetails.utils.TestDispatcherRule
import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import com.peeranm.newscorner.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.IsArticleFavouriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testDispatcherRule = TestDispatcherRule()

    private lateinit var viewModel: ArticleViewModel

    @Mock
    lateinit var repository: FavouriteArticleRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    private fun getViewModelInstance(savedStateHandle: SavedStateHandle): ArticleViewModel {
        return ArticleViewModel(
            savedStateHandle,
            InsertFavArticleUseCase(repository),
            IsArticleFavouriteUseCase(repository),
            DeleteFavArticleUseCase(repository)
        )
    }
}