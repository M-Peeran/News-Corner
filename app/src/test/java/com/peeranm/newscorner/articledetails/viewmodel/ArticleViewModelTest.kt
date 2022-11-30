package com.peeranm.newscorner.articledetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.peeranm.newscorner.utils.TestDispatcherRule
import com.peeranm.newscorner.core.constants.Constants
import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import com.peeranm.newscorner.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.IsArticleFavouriteUseCase
import com.peeranm.newscorner.utils.getArticle
import com.peeranm.newscorner.utils.getFavArticle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
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

    @Test
    fun `received non-favourite article from previous screen`() {
        // Given
        viewModel = getViewModelInstance(
            SavedStateHandle().apply {
                set(Constants.ARG_ARTICLE, getArticle())
            }
        )
        // Then
        assertThat(viewModel.isFavourite.value).isFalse()
    }

    @Test
    fun `received article from feed and it is a favourite one`() {
        testDispatcherRule.runTest {
            // When
            Mockito.`when`(
                repository.isArticleFavourite(ArgumentMatchers.anyString())
            ).thenReturn(1)

            // Given
            viewModel = getViewModelInstance(
                SavedStateHandle().apply {
                    set(Constants.ARG_ARTICLE, getArticle())
                }
            )

            delay(1000)

            // Then
            assertThat(viewModel.isFavourite.value).isTrue()
        }
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