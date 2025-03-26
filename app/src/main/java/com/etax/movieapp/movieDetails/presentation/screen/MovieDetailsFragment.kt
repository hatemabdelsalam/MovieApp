package com.etax.movieapp.movieDetails.presentation.screen

import android.app.Dialog
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.etax.movieapp.R
import com.etax.movieapp.core.data.ViewState
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.core.presentation.BaseFragment
import com.etax.movieapp.core.presentation.util.hideLoadingDialog
import com.etax.movieapp.core.presentation.util.showLoadingDialog
import com.etax.movieapp.core.presentation.util.snack
import com.etax.movieapp.databinding.FragmentMovieDetailsBinding
import com.etax.movieapp.movieDetails.presentation.viewModel.MovieDetailsViewModel
import com.etax.movieapp.utils.ConstantUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(R.layout.fragment_movie_details) {

    private val viewModel by viewModels<MovieDetailsViewModel>()
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private var movie: Movie? = null
    private lateinit var dialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val movieId = it.getLong(ConstantUtils.EXTRA_MOVIE_ID, -1)

            viewModel.getMovieDetails(movieId)

        }
    }

    override fun variableInitialization(view: View) {
        _binding = FragmentMovieDetailsBinding.bind(view)
        dialog = Dialog(requireContext())
    }

    override fun setupObserve() {
        lifecycleScope.launch {
            viewModel.movieDetails.collectLatest { state ->
                when (state) {
                    is ViewState.Error -> {
                        dialog.hideLoadingDialog()
                        view?.snack(state.errorMessage) {}
                    }

                    ViewState.Loading -> dialog.showLoadingDialog()
                    is ViewState.SuccessResult -> {
                        dialog.hideLoadingDialog()
                        binding.movie = state.result
                    }

                    else -> dialog.hideLoadingDialog()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}