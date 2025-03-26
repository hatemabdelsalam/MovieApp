package com.etax.movieapp.movieDetails.data

import com.etax.movieapp.core.data.local.MoviesDatabase
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.core.presentation.util.BatteryHelper
import com.etax.movieapp.movieDetails.domin.MovieDetailsRepository
import com.etax.movieapp.network.ApiService
import com.etax.movieapp.network.NetworkHelper
import com.etax.movieapp.network.NetworkResponse
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val moviesDatabase: MoviesDatabase,
    private val networkHelper: NetworkHelper,
    private val batteryHelper: BatteryHelper
) : MovieDetailsRepository {

    override suspend fun getMovieDetails(movieId: Long): NetworkResponse<Movie, Error> {

        return if (networkHelper.isNetworkAvailable() && batteryHelper.isBatteryGood()) {
            val movie = apiService.getMovieDetails(movieId = movieId)
            when (movie) {
                is NetworkResponse.ApiError -> NetworkResponse.ApiError(movie.body, movie.code)
                is NetworkResponse.NetworkError -> NetworkResponse.NetworkError(movie.error)
                is NetworkResponse.Success -> {
                    NetworkResponse.Success(movie.body.toMovies())
                }

                is NetworkResponse.UnknownError -> NetworkResponse.UnknownError(
                    movie.error,
                    movie.code
                )
            }
        } else {
            val movie = moviesDatabase.getMoviesDao().getMovieById(movieId)
            if (movie != null) {
                NetworkResponse.Success(movie)
            } else {
                NetworkResponse.UnknownError(Throwable("movie not found"), null)
            }
        }
    }
}