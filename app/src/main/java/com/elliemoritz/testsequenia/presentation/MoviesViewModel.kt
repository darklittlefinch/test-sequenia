package com.elliemoritz.testsequenia.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elliemoritz.testsequenia.domain.Genre
import com.elliemoritz.testsequenia.domain.Movie
import com.elliemoritz.testsequenia.domain.useCases.GetMoviesListByGenreUseCase
import com.elliemoritz.testsequenia.domain.useCases.GetMoviesListUseCase
import com.elliemoritz.testsequenia.presentation.util.getGenresFromMoviesList
import kotlinx.coroutines.launch
import retrofit2.HttpException
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

    private lateinit var movies: List<Movie>
    private lateinit var genres: List<Genre>

    fun loadData(genre: Genre? = null) {
        viewModelScope.launch {
            _state.value = MoviesState.Loading
            if (fieldsAreNotInitialized()) {
                setInitialDataToLists()
            } else if (genre != null) {
                changeSelectedGenre(genre)
            } else {
                setDataState()
            }
        }
    }

    private fun fieldsAreNotInitialized(): Boolean {
        return !this::genres.isInitialized || !this::movies.isInitialized
    }

    private fun setDataState() {
        _state.value = MoviesState.Data(movies, genres)
    }

    private suspend fun setInitialDataToLists() {
        try {
            movies = getMoviesListUseCase()
            genres = getGenresFromMoviesList(movies)
            setDataState()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun changeSelectedGenre(genre: Genre) {
        viewModelScope.launch {
            try {
                val reversedGenre = genre.copy(selected = !genre.selected)
                movies = if (reversedGenre.selected) {
                    getMoviesListByGenreUseCase(reversedGenre.name.lowercase())
                } else {
                    getMoviesListUseCase()
                }

                setSelectedGenre(reversedGenre)
                setDataState()

            } catch (e: Exception) {
                handleException(e, genre)
            }
        }
    }

    private fun setSelectedGenre(genre: Genre) {
        genres = genres.map {
            if (it.name == genre.name) {
                genre
            } else {
                it.copy(selected = false)
            }
        }
    }

    private fun handleException(e: Exception, genre: Genre? = null) {
        when (e) {
            is UnknownHostException,
            is ConnectException,
            is SocketTimeoutException,
            is HttpException -> {
                _state.value = MoviesState.Error(genre)
            }

            else -> throw e
        }
    }
}