package com.etax.movieapp.nowPlayingMovies.data


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MoviesDto(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)