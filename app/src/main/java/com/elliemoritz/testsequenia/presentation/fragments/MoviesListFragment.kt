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
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : Fragment() {

    private lateinit var onMovieClickListener: OnMovieClickListener

    private var _binding: FragmentMoviesListBinding? = null
    private val binding: FragmentMoviesListBinding
        get() = _binding ?: throw RuntimeException("FragmentMoviesListBinding == null")

    val viewModel: MoviesViewModel by viewModel()

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
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters(view.context)
        observeViewModel()
        viewModel.loadData()
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
                viewModel.loadData(genre)
            }
        }
        binding.rvGenres.adapter = genresAdapter
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is MoviesState.Data -> {
                    genresAdapter.submitList(it.genres)
                    moviesAdapter.submitList(it.movies)

                    binding.tvTitleGenres.visibility = View.VISIBLE
                    binding.tvTitleMovies.visibility = View.VISIBLE

                    binding.progressBar.visibility = View.GONE
                }

                is MoviesState.Error -> {
                    showSnackbar(it.genre)
                    binding.progressBar.visibility = View.GONE
                }

                MoviesState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showSnackbar(genre: Genre?) {
        val colorYellow = ContextCompat.getColor(requireContext(), R.color.yellow)

        val duration = if (genre == null) {
            Snackbar.LENGTH_INDEFINITE
        } else {
            Snackbar.LENGTH_LONG
        }

        val snackbar = Snackbar.make(
            binding.root,
            R.string.toast_error,
            duration
        )
            .setActionTextColor(colorYellow)
            .setAction(R.string.toast_repeat) {
                viewModel.loadData(genre)
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
        private const val MOVIES_COLUMNS_COUNT = 2
        private const val SNACKBAR_PADDING_VALUE = 16
    }
}