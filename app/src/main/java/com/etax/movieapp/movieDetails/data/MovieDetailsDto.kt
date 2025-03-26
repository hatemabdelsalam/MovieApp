package com.etax.movieapp.movieDetails.data


import androidx.annotation.Keep
import com.etax.movieapp.core.domain.Movie
import com.google.gson.annotations.SerializedName

@Keep
data class MovieDetailsDto(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("belongs_to_collection")
    var belongsToCollection: Any?,
    @SerializedName("budget")
    var budget: Int,
    @SerializedName("homepage")
    var homepage: String,
    @SerializedName("id")
    var id: Long,
    @SerializedName("imdb_id")
    var imdbId: String,
    @SerializedName("original_language")
    var originalLanguage: String,
    @SerializedName("original_title")
    var originalTitle: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("revenue")
    var revenue: Int,
    @SerializedName("runtime")
    var runtime: Int,
    @SerializedName("status")
    var status: String,
    @SerializedName("tagline")
    var tagline: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int
){
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