package com.elliemoritz.testsequenia.presentation

import com.elliemoritz.testsequenia.domain.Movie

sealed class MoviesState {
    class Movies(val value: List<Movie>) : MoviesState()
    class Genres(val value: Set<String>) : MoviesState()
    data object Loading : MoviesState()
    data object Error : MoviesState()
}