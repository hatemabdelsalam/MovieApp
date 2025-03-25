package com.etax.movieapp.nowPlayingMovies.data

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.etax.movieapp.core.data.local.MovieDao
import javax.inject.Inject

class ClearMoviesDatabaseWorkerFactory @Inject constructor(private val dao: MovieDao) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = ClearMoviesDatabaseWorker(appContext, workerParameters, dao)

}