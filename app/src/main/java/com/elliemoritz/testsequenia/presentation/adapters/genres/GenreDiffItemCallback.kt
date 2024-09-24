package com.elliemoritz.testsequenia.presentation.adapters.genres

import androidx.recyclerview.widget.DiffUtil

object GenreDiffItemCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}