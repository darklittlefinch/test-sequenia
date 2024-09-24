package com.elliemoritz.testsequenia.data.repository

import com.elliemoritz.testsequenia.data.mapper.MoviesMapper
import com.elliemoritz.testsequenia.data.network.ApiService
import com.elliemoritz.testsequenia.data.network.pojo.MoviePojo
import com.elliemoritz.testsequenia.domain.Movie
import com.elliemoritz.testsequenia.domain.MoviesRepository

class MoviesRepositoryImpl(
    private val apiService: ApiService,
    private val mapper: MoviesMapper
) : MoviesRepository {

    override suspend fun getMoviesList(): List<Movie> {
        val moviesPojoList = getMoviesPojo()
        val movies = mapper.mapListPojoToListEntity(moviesPojoList)
        return movies
    }

    override suspend fun getMoviesListByGenre(genre: String): List<Movie> {
        val moviesPojoList = getMoviesPojo()
        val movies = mapper.mapListPojoToListEntity(moviesPojoList)
            .filter { it.genres?.contains(genre) ?: return listOf() }
        return movies
    }

    private suspend fun getMoviesPojo(): List<MoviePojo> {
        val moviesPojoList = apiService.getMoviesList()
        return moviesPojoList.moviesList ?: listOf()
    }
}