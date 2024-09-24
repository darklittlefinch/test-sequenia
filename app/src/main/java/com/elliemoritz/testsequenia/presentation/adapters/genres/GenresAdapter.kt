package com.elliemoritz.testsequenia.presentation.adapters.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.elliemoritz.testsequenia.databinding.ItemGenreBinding

class GenresAdapter : ListAdapter<String, GenreViewHolder>(GenreDiffItemCallback) {

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
        holder.binding.tvGenreName.text = genre
        holder.binding.root.setOnClickListener {
            onGenreClickListener?.onGenreClick(genre)
        }
    }

    interface OnGenreClickListener {
        fun onGenreClick(genre: String)
    }
}