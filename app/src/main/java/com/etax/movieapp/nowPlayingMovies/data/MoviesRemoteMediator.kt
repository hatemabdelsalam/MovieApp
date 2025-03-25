package com.etax.movieapp.nowPlayingMovies.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.etax.movieapp.core.presentation.util.BatteryHelper
import com.etax.movieapp.network.NetworkHelper
import com.etax.movieapp.core.data.local.MoviesDatabase
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.network.ApiService
import com.etax.movieapp.network.NetworkResponse
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    val apiService: ApiService,
    val moviesDatabase: MoviesDatabase,
    private val networkHelper: NetworkHelper,
    private val batteryHelper: BatteryHelper
) : RemoteMediator<Int, Movie>() {

    var totalPages = 5
    var currentPage = 1

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try {

            val shouldFetchFromApi =
                networkHelper.isNetworkAvailable() && batteryHelper.isBatteryGood()

            if (!shouldFetchFromApi) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    if (currentPage >= totalPages) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    } else {
                        currentPage += 1
                    }
                }
            }

            Log.d("MoviesRemoteMediator", "currentPage: $currentPage")
            val responseResult = apiService.getNowPlayingMoviesList(page = currentPage)

            when (responseResult) {
                is NetworkResponse.Success -> {
                    moviesDatabase.withTransaction {

                        totalPages = responseResult.body.totalPages
                        moviesDatabase.getMoviesDao()
                            .upsertAll(responseResult.body.results.map { it.toMovies() })
                    }

                    MediatorResult.Success(endOfPaginationReached = responseResult.body.results.isEmpty())

                }

                is NetworkResponse.ApiError -> MediatorResult.Error(responseResult.body)
                is NetworkResponse.NetworkError -> MediatorResult.Error(responseResult.error)
                is NetworkResponse.UnknownError -> MediatorResult.Error(
                    responseResult.error ?: Throwable("Unknown Error")
                )
            }

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: retrofit2.HttpException) {
            MediatorResult.Error(e)
        }

    }


}