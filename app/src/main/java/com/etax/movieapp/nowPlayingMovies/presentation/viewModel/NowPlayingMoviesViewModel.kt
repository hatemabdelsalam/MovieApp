package com.etax.movieapp.nowPlayingMovies.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.etax.movieapp.nowPlayingMovies.domain.useCase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NowPlayingMoviesViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) :
    ViewModel() {

    fun getMovies() = moviesUseCase.getMoviesUseCase().cachedIn(viewModelScope)

    fun searchMovie(title: String) =
        moviesUseCase.searchMoviesUseCase(title).cachedIn(viewModelScope)

}

