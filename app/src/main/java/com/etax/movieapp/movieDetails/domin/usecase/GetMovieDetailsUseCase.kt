package com.etax.movieapp.movieDetails.domin.usecase

import androidx.paging.PagingData
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.movieDetails.domin.MovieDetailsRepository
import com.etax.movieapp.network.NetworkResponse
import com.etax.movieapp.nowPlayingMovies.domain.NowPlayingMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(val repository: MovieDetailsRepository) {
    operator suspend fun invoke(movieId: Long): NetworkResponse<Movie, Error> {
        return repository.getMovieDetails(movieId)
    }
}