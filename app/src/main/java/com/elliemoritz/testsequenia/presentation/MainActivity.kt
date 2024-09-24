package com.elliemoritz.testsequenia.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elliemoritz.testsequenia.R
import com.elliemoritz.testsequenia.domain.Movie
import com.elliemoritz.testsequenia.presentation.fragments.MovieDetailsFragment
import com.elliemoritz.testsequenia.presentation.fragments.MoviesListFragment

class MainActivity : AppCompatActivity(), OnMovieClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchMoviesListFragment()
    }

    override fun onMovieClick(movie: Movie) {
        launchMovieDetailsFragment(movie)
    }

    private fun launchMoviesListFragment() {
        val fragment = MoviesListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    private fun launchMovieDetailsFragment(movie: Movie) {
        val fragment = MovieDetailsFragment.newInstance(movie)
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, fragment)
            .addToBackStack(MovieDetailsFragment.NAME)
            .commit()
    }
}