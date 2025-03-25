package com.etax.movieapp.nowPlayingMovies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.etax.movieapp.core.domain.Movie
import com.etax.movieapp.databinding.ItemMovieBinding

class MoviesAdapter(val movieActions: MovieActions) :
    PagingDataAdapter<Movie, MoviesAdapter.ViewHolder>(MoviesDiffUtil) {

    inner class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie

            binding.root.setOnClickListener {
                movieActions.onMovieClick(movie, absoluteAdapterPosition)
            }
        }

    }

    object MoviesDiffUtil:DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
    interface MovieActions {
        fun onMovieClick(movie: Movie, index:Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}