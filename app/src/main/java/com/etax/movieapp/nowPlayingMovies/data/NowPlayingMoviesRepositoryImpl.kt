package com.etax.movieapp.nowPlayingMovies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.etax.movieapp.core.presentation.util.BatteryHelper
import com.etax.movieapp.network.NetworkHelper
import com.etax.movieapp.core.data.local.MoviesDatabase
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.network.ApiService
import com.etax.movieapp.nowPlayingMovies.domain.NowPlayingMoviesRepository
import com.etax.movieapp.utils.ConstantUtils.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NowPlayingMoviesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val moviesDatabase: MoviesDatabase,
    private val networkHelper: NetworkHelper,
    private val batteryHelper: BatteryHelper
) : NowPlayingMoviesRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE),
            remoteMediator = MoviesRemoteMediator(
                apiService,
                moviesDatabase,
                networkHelper,
                batteryHelper
            ),
            pagingSourceFactory = { moviesDatabase.getMoviesDao().getAllMovies() }
        ).flow
    }

    override fun searchMovies(title: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = { moviesDatabase.getMoviesDao().searchMovie(title) }).flow
    }
}