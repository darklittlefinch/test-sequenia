package com.elliemoritz.testsequenia.domain.useCases

import com.elliemoritz.testsequenia.domain.Movie
import com.elliemoritz.testsequenia.domain.MoviesRepository

class GetMoviesListUseCase(private val repository: MoviesRepository) {

    operator fun invoke(): List<Movie> {
        return repository.getMoviesList()
    }
}