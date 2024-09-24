package com.elliemoritz.testsequenia.presentation.adapters.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.elliemoritz.testsequenia.R
import com.elliemoritz.testsequenia.databinding.ItemGenreBinding
import com.elliemoritz.testsequenia.domain.Genre

class GenresAdapter : ListAdapter<Genre, GenreViewHolder>(GenreDiffItemCallback) {

    var onGenreClickListener: OnGenreClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)
        holder.binding.tvGenreName.text = genre.name

        val colorResId = if (genre.selected) {
            R.color.yellow
        } else {
            R.color.white
        }
        val color = ContextCompat.getColor(holder.binding.root.context, colorResId)
        holder.binding.tvGenreName.setBackgroundColor(color)

        holder.binding.root.setOnClickListener {
            onGenreClickListener?.onGenreClick(genre)
        }
    }

    interface OnGenreClickListener {
        fun onGenreClick(genre: Genre)
    }
}