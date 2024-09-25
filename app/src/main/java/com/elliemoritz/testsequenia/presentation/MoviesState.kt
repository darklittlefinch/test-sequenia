package com.elliemoritz.testsequenia.presentation

import com.elliemoritz.testsequenia.domain.Genre
import com.elliemoritz.testsequenia.domain.Movie

sealed class MoviesState {
    class Data(val movies: List<Movie>, val genres: List<Genre>) : MoviesState()
    class Error(val genre: Genre? = null) : MoviesState()
    data object Loading : MoviesState()
}