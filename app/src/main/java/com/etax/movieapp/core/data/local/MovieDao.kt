package com.etax.movieapp.core.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.etax.movieapp.core.domain.Movie

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertAll(movies: List<Movie>)

    @Query(value = "DELETE FROM movies")
    suspend fun clearMovies()

    @Query(value = "SELECT * FROM movies ORDER BY strftime('%Y-%m-%d', releaseDate) DESC")
    fun getAllMovies(): PagingSource<Int, Movie>

    @Query(value = "SELECT * FROM movies WHERE title LIKE '%' || :title || '%' ORDER BY strftime('%Y-%m-%d', releaseDate) DESC")
    fun searchMovie(title: String): PagingSource<Int, Movie>

    @Query(value = "SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Long): Movie?
}