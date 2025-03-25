package com.etax.movieapp.core.presentation

import android.app.Application
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.etax.movieapp.nowPlayingMovies.data.ClearMoviesDatabaseWorker
import com.etax.movieapp.nowPlayingMovies.data.ClearMoviesDatabaseWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: ClearMoviesDatabaseWorkerFactory

    override fun onCreate() {
        super.onCreate()

        val workRequest = PeriodicWorkRequestBuilder<ClearMoviesDatabaseWorker>(
            24,
            TimeUnit.HOURS
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofSeconds(500))
            .setInitialDelay(24, TimeUnit.HOURS).build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
    }
}