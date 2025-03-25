package com.etax.movieapp.nowPlayingMovies.presentation.screen

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.etax.movieapp.R
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.core.presentation.BaseFragment
import com.etax.movieapp.databinding.FragmentNowPlayingMoviesBinding
import com.etax.movieapp.nowPlayingMovies.presentation.adapter.MoviesAdapter
import com.etax.movieapp.nowPlayingMovies.presentation.viewModel.NowPlayingMoviesViewModel
import com.etax.movieapp.core.presentation.util.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NowPlayingMoviesFragment : BaseFragment(R.layout.fragment_now_playing_movies),
    MoviesAdapter.MovieActions {

    private var _binding: FragmentNowPlayingMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NowPlayingMoviesViewModel>()
    private lateinit var dialog: Dialog
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupListeners() {
        moviesAdapter.addLoadStateListener { loadState ->
            val errorMessage = if (loadState.refresh is LoadState.Error) {
                (loadState.refresh as LoadState.Error).error.localizedMessage
                    ?: getString(R.string.general_error_message)
            } else if (loadState.append is LoadState.Error) {
                (loadState.append as LoadState.Error).error.localizedMessage
                    ?: getString(R.string.general_error_message)
            } else if (loadState.prepend is LoadState.Error) {
                (loadState.prepend as LoadState.Error).error.localizedMessage
                    ?: getString(R.string.general_error_message)
            } else
                ""

            if (errorMessage.isNotEmpty()) {
                view?.snack(errorMessage) {}
            }

        }

        binding.searchEdt.addTextChangedListener { text ->
            if (text.toString().isNotEmpty()) {
                lifecycleScope.launch {
                    viewModel.searchMovie(text.toString()).collectLatest {
                        if (text.toString().isNotEmpty()) {
                            moviesAdapter.submitData(it)
                        }
                    }
                }
            }
        }
    }

    override fun setupObserve() {
        lifecycleScope.launch {
            viewModel.getMovies().collectLatest {
                moviesAdapter.submitData(it)
            }
        }
    }

    override fun variableInitialization(view: View) {
        _binding = FragmentNowPlayingMoviesBinding.bind(view)
        dialog = Dialog(requireContext())
        moviesAdapter = MoviesAdapter(this)
        moviesAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.moviesRv.adapter = moviesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(movie: Movie, index: Int) {
        //TODO("Not yet implemented")
    }

}