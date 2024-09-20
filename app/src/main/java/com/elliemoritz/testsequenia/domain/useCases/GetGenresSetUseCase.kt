package com.elliemoritz.testsequenia.domain.useCases

import com.elliemoritz.testsequenia.domain.MoviesRepository

class GetGenresSetUseCase(private val repository: MoviesRepository) {

    operator fun invoke(): Set<String> {
        return repository.getGenresSet()
    }
}