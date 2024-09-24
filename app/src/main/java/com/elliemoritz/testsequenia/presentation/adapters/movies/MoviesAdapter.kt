package com.elliemoritz.testsequenia.presentation.adapters.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.elliemoritz.testsequenia.R
import com.elliemoritz.testsequenia.databinding.ItemMovieBinding
import com.elliemoritz.testsequenia.domain.Movie

class MoviesAdapter : ListAdapter<Movie, MovieViewHolder>(MovieDiffItemCallback) {

    var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder.binding) {
            tvMovieLocalizedName.text = movie.localizedName

            Glide.with(holder.binding.root.context)
                .load(movie.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_no_image)
                .into(holder.binding.ivPoster)

            root.setOnClickListener {
                onMovieClickListener?.onMovieClick(movie)
            }
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }
}