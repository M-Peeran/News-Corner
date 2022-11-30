package com.peeranm.newscorner.articledetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.peeranm.newscorner.utils.TestDispatcherRule
import com.peeranm.newscorner.core.constants.Constants
import com.peeranm.newscorner.favouritearticles.data.local.entity.FavArticle
import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import com.peeranm.newscorner.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.IsArticleFavouriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    @Test
    fun `received favourite article from previous screen`() {
        // Given
        viewModel = getViewModelInstance(
            SavedStateHandle().apply {
                set(Constants.ARG_FAV_ARTICLE, getFavArticle())
            }
        )
        // Then
        assertThat(viewModel.isFavourite.value).isTrue()
    }

    private fun getViewModelInstance(savedStateHandle: SavedStateHandle): ArticleViewModel {
        return ArticleViewModel(
            savedStateHandle,
            InsertFavArticleUseCase(repository),
            IsArticleFavouriteUseCase(repository),
            DeleteFavArticleUseCase(repository)
        )
    }

    private fun getFavArticle() = FavArticle(
        title = "Newton will make upcoming engineers cry!",
        description = "Gravity made me famous, what you want else in life?",
        author = "Einstein",
        content = "Gravity made me famous, what you want else in life?",
        publishedAt = "2022-01-01T17:24:04Z",
        source = "Einstein's Personal Journal",
        url = "Newton will kill me if I publish this...",
        urlToImage = "Ask Newton"
    )
}