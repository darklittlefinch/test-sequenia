package com.elliemoritz.testsequenia.domain

interface MoviesRepository {
    suspend fun getMoviesList(): List<Movie>
    suspend fun getMoviesListByGenre(genre: String): List<Movie>
}