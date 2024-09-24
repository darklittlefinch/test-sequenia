package com.elliemoritz.testsequenia.presentation.adapters.genres

import androidx.recyclerview.widget.DiffUtil
import com.elliemoritz.testsequenia.domain.Genre

object GenreDiffItemCallback : DiffUtil.ItemCallback<Genre>() {

    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem == newItem
    }
}