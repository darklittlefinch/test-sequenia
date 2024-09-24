package com.elliemoritz.testsequenia.data.network

import com.elliemoritz.testsequenia.data.network.pojo.MoviesListContainerPojo
import retrofit2.http.GET

interface ApiService {

    @GET("films.json")
    suspend fun getMoviesList(): MoviesListContainerPojo
}