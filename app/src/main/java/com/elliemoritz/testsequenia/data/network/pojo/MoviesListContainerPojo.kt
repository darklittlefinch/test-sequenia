package com.elliemoritz.testsequenia.data.network.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesListContainerPojo(

    @SerializedName("films")
    @Expose
    val moviesList: List<MoviePojo>?
)