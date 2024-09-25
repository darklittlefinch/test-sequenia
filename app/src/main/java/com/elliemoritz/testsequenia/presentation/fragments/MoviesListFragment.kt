package com.elliemoritz.testsequenia.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.elliemoritz.testsequenia.R
import com.elliemoritz.testsequenia.databinding.FragmentMoviesListBinding
import com.elliemoritz.testsequenia.domain.Genre
import com.elliemoritz.testsequenia.domain.Movie
import com.elliemoritz.testsequenia.presentation.MoviesState
import com.elliemoritz.testsequenia.presentation.MoviesViewModel
import com.elliemoritz.testsequenia.presentation.OnMovieClickListener
import com.elliemoritz.testsequenia.presentation.adapters.genres.GenresAdapter
import com.elliemoritz.testsequenia.presentation.adapters.movies.MoviesAdapter
import com.elliemoritz.testsequenia.presentation.util.parcelableArrayList
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class MoviesListFragment : Fragment() {

    private lateinit var onMovieClickListener: OnMovieClickListener

    private var _binding: FragmentMoviesListBinding? = null
    private val binding: FragmentMoviesListBinding
        get() = _binding ?: throw RuntimeException("FragmentMoviesListBinding == null")

    private val viewModel by inject<MoviesViewModel>()

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var genresAdapter: GenresAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnMovieClickListener) {
            onMovieClickListener = context
        } else {
            throw RuntimeException("Activity must implement OnMovieClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        savedInstanceState?.parcelableArrayList<Genre>(KEY_GENRE)?.let {
            viewModel.setSelectedGenre(it)
        }

        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters(view.context)
        observeViewModel()
        viewModel.loadData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val selectedGenre = viewModel.getGenres()
        outState.putParcelableArrayList(KEY_GENRE, selectedGenre as ArrayList)
    }

    private fun setAdapters(context: Context) {
        moviesAdapter = MoviesAdapter()
        moviesAdapter.onMovieClickListener = object : MoviesAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                onMovieClickListener.onMovieClick(movie)
            }
        }
        binding.rvMovies.adapter = moviesAdapter
        binding.rvMovies.layoutManager = GridLayoutManager(context, MOVIES_COLUMNS_COUNT)

        genresAdapter = GenresAdapter()
        genresAdapter.onGenreClickListener = object : GenresAdapter.OnGenreClickListener {
            override fun onGenreClick(genre: Genre) {
                viewModel.changeSelectedGenre(genre)
            }
        }
        binding.rvGenres.adapter = genresAdapter
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                MoviesState.Error -> {
                    showSnackbar()
                    binding.progressBar.visibility = View.GONE
                }

                is MoviesState.Genres -> {
                    genresAdapter.submitList(it.genresList)
                }

                MoviesState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is MoviesState.Movies -> {
                    moviesAdapter.submitList(it.moviesList)
                    binding.progressBar.visibility = View.GONE
                    setViewsVisibility()
                }
            }
        }
    }

    private fun setViewsVisibility() {
        binding.tvTitleGenres.visibility = View.VISIBLE
        binding.tvTitleMovies.visibility = View.VISIBLE
        binding.rvGenres.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.VISIBLE
    }

    private fun showSnackbar() {
        val colorYellow = ContextCompat.getColor(requireContext(), R.color.yellow)

        val snackbar = Snackbar.make(
            binding.root,
            R.string.toast_error,
            Snackbar.LENGTH_INDEFINITE
        )
            .setActionTextColor(colorYellow)
            .setAction(R.string.toast_repeat) {
                viewModel.loadData()
            }

        val snackbarView = snackbar.view
        snackbarView.setPadding(
            SNACKBAR_PADDING_VALUE,
            SNACKBAR_PADDING_VALUE,
            SNACKBAR_PADDING_VALUE,
            SNACKBAR_PADDING_VALUE
        )

        snackbar.show()
    }

    companion object {
        private const val KEY_GENRE = "genre"
        private const val MOVIES_COLUMNS_COUNT = 2
        private const val SNACKBAR_PADDING_VALUE = 16
    }
}