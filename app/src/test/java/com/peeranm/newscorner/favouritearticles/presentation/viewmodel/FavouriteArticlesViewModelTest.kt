package com.peeranm.newscorner.favouritearticles.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import com.peeranm.newscorner.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.GetFavArticlesUseCase
import com.peeranm.newscorner.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.newscorner.utils.TestDispatcherRule
import com.peeranm.newscorner.utils.getFavArticle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteArticlesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testDispatcherRule = TestDispatcherRule()

    private lateinit var viewModel: FavouriteArticlesViewModel

    @Mock
    lateinit var repository: FavouriteArticleRepository

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = FavouriteArticlesViewModel(
            GetFavArticlesUseCase(repository),
            DeleteFavArticleUseCase(repository),
            InsertFavArticleUseCase(repository)
        )
    }

    @Test
    fun `connectionLiveData is initialized properly`() {
        // Given
        Mockito.`when`(context.getSystemService(ArgumentMatchers.anyString()))
            .thenReturn(connectivityManager)

        // When
        viewModel.initializeConnectionLiveData(context)

        // Then
        assertThat(viewModel.connectionLiveData).isNotNull()
    }

    @Test
    fun `received empty list when favourite articles fetched from cache`() {
        testDispatcherRule.runTest {
            // Given
            Mockito.`when`(repository.getFavArticles()).thenReturn(emptyFlow())

            // When
            viewModel.getFavArticles()

            // Then
            viewModel.favArticles.test {
                assertThat(awaitItem()).isEmpty()
                Mockito.verify(repository, times(1)).getFavArticles()
            }
        }
    }

    @Test
    fun `received favourite articles from cache`() {
        testDispatcherRule.runTest {
            // Given
            Mockito.`when`(repository.getFavArticles()).thenReturn(flowOf(listOf(getFavArticle())))

            // When
            viewModel.getFavArticles()

            // Then
            viewModel.favArticles.drop(1).test {
                assertThat(awaitItem()).isNotEmpty()
                Mockito.verify(repository, times(1)).getFavArticles()
            }
        }
    }
}