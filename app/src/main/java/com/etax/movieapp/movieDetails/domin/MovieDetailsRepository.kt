package com.etax.movieapp.movieDetails.domin

import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.network.NetworkResponse

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Long): NetworkResponse<Movie, Error>
}