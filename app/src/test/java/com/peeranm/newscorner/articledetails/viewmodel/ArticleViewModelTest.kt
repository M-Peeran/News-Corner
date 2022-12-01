package com.peeranm.newscorner.articledetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.peeranm.newscorner.utils.TestDispatcherRule
import com.peeranm.newscorner.core.constants.Constants
import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import com.peeranm.newscorner.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.IsArticleFavouriteUseCase
import com.peeranm.newscorner.utils.getArticle
import com.peeranm.newscorner.utils.getFavArticle
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import kotlin.time.*

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
        testDispatcherRule.runTest {
            // Given
            viewModel = getViewModelInstance(
                SavedStateHandle().apply {
                    set(Constants.ARG_FAV_ARTICLE, getFavArticle())
                }
            )

            // Then
            viewModel.isFavourite.test {
                assertThat(awaitItem()).isTrue()
            }
        }
    }

    @Test
    fun `received non-favourite article from previous screen`() {
        testDispatcherRule.runTest {
            // Given
            viewModel = getViewModelInstance(
                SavedStateHandle().apply {
                    set(Constants.ARG_ARTICLE, getArticle())
                }
            )

            // Then
            viewModel.isFavourite.test {
                assertThat(awaitItem()).isFalse()
            }
        }
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

            // Then
            viewModel.isFavourite.test {
                assertThat(awaitItem()).isFalse()
                assertThat(awaitItem()).isTrue()
            }
        }
    }

    @Test
    fun `deleted the given favourite article`() {
        testDispatcherRule.runTest {

            // Given
            Mockito.`when`(repository.deleteFavArticleById(ArgumentMatchers.anyString()))
                .thenReturn(Unit)

            viewModel = getViewModelInstance(
                SavedStateHandle().apply {
                    set(Constants.ARG_FAV_ARTICLE, getFavArticle())
                }
            )

            // When
            viewModel.saveOrDeleteArticle()

            // Then
            viewModel.isFavourite.test {
                assertThat(awaitItem()).isTrue()
                assertThat(awaitItem()).isFalse()
                Mockito.verify(repository, times(1)).deleteFavArticleById(ArgumentMatchers.anyString())
            }
        }
    }

    @Test
    fun `no article found`() {
        // Given
        viewModel = getViewModelInstance(SavedStateHandle())
        // Then
        assertThat(viewModel.noArticleFound).isTrue()
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