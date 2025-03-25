package com.etax.movieapp.nowPlayingMovies.domain.useCase

import androidx.paging.PagingData
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.nowPlayingMovies.domain.NowPlayingMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(val repository: NowPlayingMoviesRepository) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return repository.getMovies()
    }
}