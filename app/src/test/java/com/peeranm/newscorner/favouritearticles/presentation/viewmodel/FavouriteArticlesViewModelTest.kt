package com.peeranm.newscorner.favouritearticles.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peeranm.newscorner.favouritearticles.data.repository.FavouriteArticleRepository
import com.peeranm.newscorner.favouritearticles.usecase.DeleteFavArticleUseCase
import com.peeranm.newscorner.favouritearticles.usecase.GetFavArticlesUseCase
import com.peeranm.newscorner.favouritearticles.usecase.InsertFavArticleUseCase
import com.peeranm.newscorner.utils.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
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

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = FavouriteArticlesViewModel(
            GetFavArticlesUseCase(repository),
            DeleteFavArticleUseCase(repository),
            InsertFavArticleUseCase(repository)
        )
    }
}