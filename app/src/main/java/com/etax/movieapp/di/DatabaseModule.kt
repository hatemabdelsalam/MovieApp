package com.etax.movieapp.di

import android.content.Context
import com.etax.movieapp.core.data.local.MovieDao
import com.etax.movieapp.core.data.local.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
        return MoviesDatabase.invoke(appContext)
    }

    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MovieDao {
        return moviesDatabase.getMoviesDao()
    }

}