package com.etax.movieapp.network

import com.etax.movieapp.BuildConfig


object Urls {
    const val BASE_URL = BuildConfig.BASE_URL
    const val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL
    const val NOW_PLAYING_MOVIES = "3/movie/now_playing?"
    const val MOVIE_DETAILS = "3/movie/{movie_id}"

}