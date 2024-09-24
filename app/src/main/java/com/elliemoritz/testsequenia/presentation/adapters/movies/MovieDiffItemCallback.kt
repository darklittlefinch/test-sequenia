package com.elliemoritz.testsequenia.presentation.adapters.movies

import androidx.recyclerview.widget.DiffUtil
import com.elliemoritz.testsequenia.domain.Movie

object MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}