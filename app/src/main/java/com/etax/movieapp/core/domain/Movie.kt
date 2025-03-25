package com.etax.movieapp.core.domain

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val overview : String,
    val originalLanguage : String
): Parcelable
