package com.elliemoritz.testsequenia.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elliemoritz.testsequenia.domain.Genre
import com.elliemoritz.testsequenia.domain.Movie
import com.elliemoritz.testsequenia.domain.useCases.GetMoviesListByGenreUseCase
import com.elliemoritz.testsequenia.domain.useCases.GetMoviesListUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MoviesViewModel(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val getMoviesListByGenreUseCase: GetMoviesListByGenreUseCase
) : ViewModel() {

    private val _state = MutableLiveData<MoviesState>()
    val state: LiveData<MoviesState>
        get() = _state

    private lateinit var allGenres: List<Genre>

    fun getData() {
        _state.value = MoviesState.Loading
        viewModelScope.launch {
            try {
                val movies = getMoviesListUseCase()
                _state.value = MoviesState.Movies(movies)
                allGenres = getGenresFromMoviesList(movies)
                _state.value = MoviesState.Genres(allGenres)
            } catch (e: HttpException) {
                _state.value = MoviesState.Error
            }
        }
    }

    fun changeSelectedGenre(genre: Genre) {
        _state.value = MoviesState.Loading
        val newGenreValue = genre.copy(selected = !genre.selected)
        viewModelScope.launch {
            if (newGenreValue.selected) {
                getMoviesByGenre(newGenreValue)
            } else {
                getAllMovies()
            }
        }
    }

    private suspend fun getAllMovies() {
        try {
            val movies = getMoviesListUseCase()
            _state.value = MoviesState.Movies(movies)
            allGenres = allGenres.map { it.copy(selected = false) }
            _state.value = MoviesState.Genres(allGenres)
        } catch (e: HttpException) {
            _state.value = MoviesState.Error
        }
    }

    private suspend fun getMoviesByGenre(genre: Genre) {
        try {
            val movies = getMoviesListByGenreUseCase(genre.name.lowercase())
            _state.value = MoviesState.Movies(movies)
            allGenres = allGenres.map {
                if (it.name == genre.name) {
                    it.copy(selected = genre.selected)
                } else {
                    it.copy(selected = false)
                }
            }
            _state.value = MoviesState.Genres(allGenres)
        } catch (e: HttpException) {
            _state.value = MoviesState.Error
        }
    }

    private fun getGenresFromMoviesList(
        moviesList: List<Movie>
    ): List<Genre> = moviesList
        .flatMap { it.genres }
        .distinct()
        .map { Genre(getCapitalizedString(it), false) }

    private fun getCapitalizedString(string: String): String {
        val firstLetter = string.substring(0, 1).uppercase()
        val restOfString = string.substring(1).lowercase()
        return firstLetter + restOfString
    }
}