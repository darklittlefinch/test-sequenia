package com.elliemoritz.testsequenia.di

import com.elliemoritz.testsequenia.domain.useCases.GetMoviesListByGenreUseCase
import com.elliemoritz.testsequenia.domain.useCases.GetMoviesListUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetMoviesListUseCase(get()) }
    single { GetMoviesListByGenreUseCase(get()) }
}