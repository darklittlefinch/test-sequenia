package com.elliemoritz.testsequenia.presentation

import com.elliemoritz.testsequenia.domain.Movie

interface OnMovieClickListener {
    fun onMovieClick(movie: Movie)
}