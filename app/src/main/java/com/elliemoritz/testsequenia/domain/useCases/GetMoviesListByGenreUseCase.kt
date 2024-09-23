package com.elliemoritz.testsequenia.domain.useCases

import com.elliemoritz.testsequenia.domain.Movie
import com.elliemoritz.testsequenia.domain.MoviesRepository

class GetMoviesListByGenreUseCase(private val repository: MoviesRepository) {

    operator fun invoke(genre: String): List<Movie> {
        return repository.getMoviesListByGenre(genre)
    }
}