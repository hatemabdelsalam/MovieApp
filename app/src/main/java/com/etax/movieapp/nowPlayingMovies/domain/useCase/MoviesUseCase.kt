package com.etax.movieapp.nowPlayingMovies.domain.useCase

data class MoviesUseCase(
    val getMoviesUseCase: GetMoviesUseCase,
    val searchMoviesUseCase: SearchMoviesUseCase
)