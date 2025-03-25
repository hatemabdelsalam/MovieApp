package com.etax.movieapp.di

import android.content.Context
import com.etax.movieapp.core.presentation.util.BatteryHelper
import com.etax.movieapp.network.NetworkHelper
import com.etax.movieapp.core.data.local.MoviesDatabase
import com.etax.movieapp.network.ApiService
import com.etax.movieapp.nowPlayingMovies.data.NowPlayingMoviesRepositoryImpl
import com.etax.movieapp.nowPlayingMovies.domain.NowPlayingMoviesRepository
import com.etax.movieapp.nowPlayingMovies.domain.useCase.GetMoviesUseCase
import com.etax.movieapp.nowPlayingMovies.domain.useCase.MoviesUseCase
import com.etax.movieapp.nowPlayingMovies.domain.useCase.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        apiService: ApiService,
        moviesDatabase: MoviesDatabase,
        networkHelper: NetworkHelper,
        batteryHelper: BatteryHelper
    ): NowPlayingMoviesRepository =
        NowPlayingMoviesRepositoryImpl(apiService, moviesDatabase, networkHelper, batteryHelper)

    @Provides
    @Singleton
    fun provideMoviesUseCases(repository: NowPlayingMoviesRepository): MoviesUseCase {
        return MoviesUseCase(
            getMoviesUseCase = GetMoviesUseCase(repository),
            searchMoviesUseCase = SearchMoviesUseCase(repository)
        )
    }

    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }

    @Provides
    fun provideBatteryHelper(@ApplicationContext context: Context): BatteryHelper {
        return BatteryHelper(context)
    }


}