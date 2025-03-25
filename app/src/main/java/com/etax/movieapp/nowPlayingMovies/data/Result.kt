package com.etax.movieapp.nowPlayingMovies.data


import androidx.annotation.Keep
import com.etax.movieapp.core.domain.Movie
import com.google.gson.annotations.SerializedName

@Keep
data class Result(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    fun toMovies() = Movie(
        id = id,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        overview = overview,
        originalLanguage = originalLanguage
    )
}