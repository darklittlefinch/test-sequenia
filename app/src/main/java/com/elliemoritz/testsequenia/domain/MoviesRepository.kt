package com.elliemoritz.testsequenia.domain

interface MoviesRepository {
    fun getMoviesList(): List<Movie>
    fun getMovie(): Movie
    fun getGenresSet(): Set<String>
}