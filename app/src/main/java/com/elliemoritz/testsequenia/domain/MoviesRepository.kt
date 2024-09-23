package com.elliemoritz.testsequenia.domain

interface MoviesRepository {
    fun getMoviesList(): List<Movie>
    fun getMoviesListByGenre(genre: String): List<Movie>
    fun getMovie(id: Int): Movie
    fun getGenresSet(): Set<String>
}