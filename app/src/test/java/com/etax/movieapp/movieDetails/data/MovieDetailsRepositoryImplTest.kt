package com.etax.movieapp.movieDetails.data

import com.etax.movieapp.core.data.local.MovieDao
import com.etax.movieapp.core.data.local.MoviesDatabase
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.core.presentation.util.BatteryHelper
import com.etax.movieapp.network.ApiService
import com.etax.movieapp.network.NetworkHelper
import com.etax.movieapp.network.NetworkResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieDetailsRepositoryImplTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var moviesDatabase: MoviesDatabase

    @Mock
    private lateinit var moviesDao: MovieDao

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var batteryHelper: BatteryHelper

    private lateinit var repository: MovieDetailsRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(moviesDatabase.getMoviesDao()).thenReturn(moviesDao)
        repository =
            MovieDetailsRepositoryImpl(apiService, moviesDatabase, networkHelper, batteryHelper)
    }

    @Test
    fun `getMovieDetails - network available and battery good - success`() = runTest {
        // Arrange
        val movieId = 1L
        val expectedMovie = MovieDetailsDto(
            id = movieId,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "test_poster_path",
            releaseDate = "2023-01-01",
            voteAverage = 7.5,
            originalLanguage = "en",
            runtime = 120,
            budget = 1000000,
            revenue = 2000000,
            status = "Released",
            tagline = "Test Tagline",
            homepage = "https://www.testmovie.com",
            adult = false,
            video = false,
            voteCount = 100,
            popularity = 5.0,
            imdbId = "tt1234567",
            backdropPath = "test_backdrop_path",
            originalTitle = "Test Movie",
            belongsToCollection = "Test Collection"
        )
        val apiResponse = NetworkResponse.Success(expectedMovie)

        `when`(networkHelper.isNetworkAvailable()).thenReturn(true)
        `when`(batteryHelper.isBatteryGood()).thenReturn(true)
        `when`(apiService.getMovieDetails(movieId)).thenReturn(apiResponse)

        // Act
        val result = repository.getMovieDetails(movieId)

        // Assert
        assertEquals(NetworkResponse.Success(expectedMovie.toMovies()), result)
    }

    @Test
    fun `getMovieDetails - network available and battery good - network error`() = runTest {
        // Arrange
        val movieId = 1L
        val expectedError = IOException("Network Error")
        val apiResponse = NetworkResponse.NetworkError(expectedError)

        `when`(networkHelper.isNetworkAvailable()).thenReturn(true)
        `when`(batteryHelper.isBatteryGood()).thenReturn(true)
        `when`(apiService.getMovieDetails(movieId)).thenReturn(apiResponse)

        // Act
        val result = repository.getMovieDetails(movieId)

        // Assert
        assertEquals(NetworkResponse.NetworkError(expectedError), result)
    }

    @Test
    fun `getMovieDetails - network available and battery good - unknown error`() = runTest {
        // Arrange
        val movieId = 1L
        val expectedError = Throwable("Unknown Error")
        val apiResponse = NetworkResponse.UnknownError(expectedError, 500)

        `when`(networkHelper.isNetworkAvailable()).thenReturn(true)
        `when`(batteryHelper.isBatteryGood()).thenReturn(true)
        `when`(apiService.getMovieDetails(movieId)).thenReturn(apiResponse)

        // Act
        val result = repository.getMovieDetails(movieId)

        // Assert
        assertEquals(NetworkResponse.UnknownError(expectedError, 500), result)
    }

    @Test
    fun `getMovieDetails - network unavailable - movie found in database`() = runTest {
        // Arrange
        val movieId = 1L
        val expectedMovie = Movie(
            id = movieId,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "test_poster_path",
            releaseDate = "2023-01-01",
            voteAverage = 7.5,
            originalLanguage = "en"
        )

        `when`(networkHelper.isNetworkAvailable()).thenReturn(false)
        `when`(moviesDao.getMovieById(movieId)).thenReturn(expectedMovie)

        // Act
        val result = repository.getMovieDetails(movieId)

        // Assert
        assertEquals(NetworkResponse.Success(expectedMovie), result)
    }

    @Test
    fun `getMovieDetails - network unavailable - movie not found in database`() = runTest {
        // Arrange
        val movieId = 1L

        `when`(networkHelper.isNetworkAvailable()).thenReturn(false)
        `when`(batteryHelper.isBatteryGood()).thenReturn(true)
        `when`(moviesDao.getMovieById(movieId)).thenReturn(null)

        // Act
        val result = repository.getMovieDetails(movieId)

        // Assert
        assertEquals(
            NetworkResponse.UnknownError(Throwable("movie not found"), null),
            result
        )
    }

    @Test
    fun `getMovieDetails - battery not good - movie found in database`() = runTest {
        // Arrange
        val movieId = 1L
        val expectedMovie = Movie(
            id = movieId,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "test_poster_path",
            releaseDate = "2023-01-01",
            voteAverage = 7.5,
            originalLanguage = "en"
        )

        `when`(networkHelper.isNetworkAvailable()).thenReturn(true)
        `when`(batteryHelper.isBatteryGood()).thenReturn(false)
        `when`(moviesDao.getMovieById(movieId)).thenReturn(expectedMovie)

        // Act
        val result = repository.getMovieDetails(movieId)

        // Assert
        assertEquals(NetworkResponse.Success(expectedMovie), result)
    }

    @Test
    fun `getMovieDetails - battery not good - movie not found in database`() = runTest {
        // Arrange
        val movieId = 1L

        `when`(networkHelper.isNetworkAvailable()).thenReturn(true)
        `when`(batteryHelper.isBatteryGood()).thenReturn(false)
        `when`(moviesDao.getMovieById(movieId)).thenReturn(null)

        // Act
        val result = repository.getMovieDetails(movieId)

        // Assert
        assertEquals(
            NetworkResponse.UnknownError(Throwable("movie not found"), null),
            result
        )
    }
}