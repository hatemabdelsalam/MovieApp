package com.etax.movieapp.nowPlayingMovies.data

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.etax.movieapp.core.data.local.MovieDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ClearMoviesDatabaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val movieDao: MovieDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        try {
            movieDao.clearMovies()
            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }
}