package com.etax.movieapp.nowPlayingMovies.domain.useCase

import androidx.paging.PagingData
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.nowPlayingMovies.domain.NowPlayingMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val repository: NowPlayingMoviesRepository) {
    operator fun invoke(query: String): Flow<PagingData<Movie>> {
        return repository.searchMovies(query)
    }

}