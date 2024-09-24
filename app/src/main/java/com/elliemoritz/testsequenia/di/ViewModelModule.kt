package com.elliemoritz.testsequenia.di

import com.elliemoritz.testsequenia.presentation.MoviesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MoviesViewModel(get(), get()) }
}