package com.etax.movieapp.movieDetails.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etax.movieapp.core.data.ViewState
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.movieDetails.domin.usecase.GetMovieDetailsUseCase
import com.etax.movieapp.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(val getMovieDetailsUseCase: GetMovieDetailsUseCase) :
    ViewModel() {

    private val _movieDetails = MutableStateFlow<ViewState<Movie>>(ViewState.Idle)
    val movieDetails = _movieDetails.asStateFlow()

    fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            _movieDetails.value = ViewState.Loading
            when (val response = getMovieDetailsUseCase(movieId)) {
                is NetworkResponse.Success -> _movieDetails.value = ViewState.SuccessResult(response.body)
                else -> _movieDetails.value = ViewState.Error(response.toString())
            }

        }
    }

}