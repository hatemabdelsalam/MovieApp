package com.etax.movieapp.network

import com.etax.movieapp.BuildConfig
import com.etax.movieapp.nowPlayingMovies.data.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Urls.NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMoviesList(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): NetworkResponse<MoviesDto, Error>
}