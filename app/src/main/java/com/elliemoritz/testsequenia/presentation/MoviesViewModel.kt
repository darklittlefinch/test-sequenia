package com.elliemoritz.testsequenia.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elliemoritz.testsequenia.domain.Genre
import com.elliemoritz.testsequenia.domain.useCases.GetMoviesListByGenreUseCase
import com.elliemoritz.testsequenia.domain.useCases.GetMoviesListUseCase
import com.elliemoritz.testsequenia.presentation.util.getGenresFromMoviesList
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MoviesViewModel(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val getMoviesListByGenreUseCase: GetMoviesListByGenreUseCase
) : ViewModel() {

    private val _state = MutableLiveData<MoviesState>()
    val state: LiveData<MoviesState>
        get() = _state

    private lateinit var allGenres: List<Genre>

    fun loadData() {
        viewModelScope.launch {
            loadMovies()
        }
    }

    fun changeSelectedGenre(genre: Genre) {
        val newSelectedValue = !genre.selected
        allGenres = allGenres.map {
            if (it.name == genre.name) {
                it.copy(selected = newSelectedValue)
            } else {
                it.copy(selected = false)
            }
        }

        viewModelScope.launch {
            loadMovies()
        }
    }

    private suspend fun loadMovies() {
        try {
            _state.value = MoviesState.Loading
            if (this@MoviesViewModel::allGenres.isInitialized) {
                val selectedGenre = allGenres.firstOrNull() { it.selected }
                if (selectedGenre == null) {
                    getAllMovies()
                } else {
                    getMoviesByGenre(selectedGenre)
                }
            } else {
                val movies = getMoviesListUseCase()
                _state.value = MoviesState.Movies(movies)
                allGenres = getGenresFromMoviesList(movies)
                _state.value = MoviesState.Genres(allGenres)
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is ConnectException, is SocketTimeoutException -> {
                    _state.value = MoviesState.Error
                }

                else -> throw e
            }
        }
    }

    private suspend fun getAllMovies() {
        val movies = getMoviesListUseCase()
        _state.value = MoviesState.Movies(movies)
        _state.value = MoviesState.Genres(allGenres)
    }

    private suspend fun getMoviesByGenre(genre: Genre) {
        val movies = getMoviesListByGenreUseCase(genre.name.lowercase())
        _state.value = MoviesState.Movies(movies)
        _state.value = MoviesState.Genres(allGenres)
    }
}