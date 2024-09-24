package com.elliemoritz.testsequenia.presentation

import com.elliemoritz.testsequenia.domain.Genre
import com.elliemoritz.testsequenia.domain.Movie

sealed class MoviesState {
    class Movies(val moviesList: List<Movie>) : MoviesState()
    class Genres(val genresList: List<Genre>) : MoviesState()
    data object Loading : MoviesState()
    data object Error : MoviesState()
}