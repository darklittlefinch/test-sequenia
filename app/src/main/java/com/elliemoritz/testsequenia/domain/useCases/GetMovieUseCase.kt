package com.elliemoritz.testsequenia.domain.useCases

import com.elliemoritz.testsequenia.domain.Movie
import com.elliemoritz.testsequenia.domain.MoviesRepository

class GetMovieUseCase(private val repository: MoviesRepository) {

    operator fun invoke(id: Int): Movie {
        return repository.getMovie(id)
    }
}