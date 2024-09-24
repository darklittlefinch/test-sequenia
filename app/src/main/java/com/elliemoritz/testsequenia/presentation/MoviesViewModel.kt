package com.elliemoritz.testsequenia.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun getData() {
        _state.value = MoviesState.Loading

        viewModelScope.launch {
            try {
                val movies = getMoviesListUseCase()
                _state.value = MoviesState.Movies(movies)

                val genres = getGenresFromMoviesList(movies)
                _state.value = MoviesState.Genres(genres)
            } catch (e: HttpException) {
                _state.value = MoviesState.Error
                Log.d("MoviesViewModel", e.message())
            }
        }
    }

    fun getMoviesByGenre(genre: String) {
        viewModelScope.launch {
            try {
                val movies = getMoviesListByGenreUseCase(genre)
                _state.value = MoviesState.Movies(movies)
            } catch (e: HttpException) {
                _state.value = MoviesState.Error
            }
        }
    }

    private fun getGenresFromMoviesList(moviesList: List<Movie>): Set<String> = moviesList
        .flatMap { it.genres ?: throw RuntimeException("No genres") }
        .toSortedSet()
}