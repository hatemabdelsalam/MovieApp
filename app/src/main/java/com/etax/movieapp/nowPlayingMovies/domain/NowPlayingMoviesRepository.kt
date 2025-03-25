package com.etax.movieapp.nowPlayingMovies.domain

import androidx.paging.PagingData
import com.etax.movieapp.core.domain.Movie
import kotlinx.coroutines.flow.Flow

interface NowPlayingMoviesRepository {
    fun getMovies():Flow<PagingData<Movie>>

    fun searchMovies(title: String): Flow<PagingData<Movie>>

}