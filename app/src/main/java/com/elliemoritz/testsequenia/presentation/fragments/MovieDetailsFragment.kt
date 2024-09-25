package com.elliemoritz.testsequenia.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.elliemoritz.testsequenia.R
import com.elliemoritz.testsequenia.databinding.FragmentMovieDetailsBinding
import com.elliemoritz.testsequenia.domain.Movie
import com.elliemoritz.testsequenia.presentation.util.formatGenres
import com.elliemoritz.testsequenia.presentation.util.formatRating
import com.elliemoritz.testsequenia.presentation.util.parcelable

class MovieDetailsFragment : Fragment() {

    private var movie: Movie? = null

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieDetailsBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.parcelable<Movie>(ARG_MOVIE)?.let {
            movie = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {

        if (movie == null) {
            throw RuntimeException("Movie is absent")
        }

        setMoviesNames()
        setPoster()
        setGenreAndYear()
        setRating()
        setDescription()
        setOnBackClickListener()
    }

    private fun setMoviesNames() {
        binding.tvMovieName.text = movie?.name
        binding.tvMovieLocalizedName.text = movie?.localizedName
    }

    private fun setPoster() {
        Glide.with(requireActivity())
            .load(movie?.imageUrl)
            .placeholder(R.drawable.ic_no_image)
            .error(R.drawable.ic_no_image)
            .into(binding.ivPoster)
    }

    private fun setGenreAndYear() {
        binding.tvGenresAndYear.text = getString(
            R.string.genres_and_year,
            formatGenres(movie?.genres),
            movie?.year
        )
    }

    private fun setRating() {
        if (movie?.rating != null) {
            val ratingOwner = getString(R.string.title_kinopoisk)
            binding.tvRatingOwner.text = ratingOwner
            binding.tvRating.text = formatRating(movie?.rating)
        } else {
            binding.tvRating.visibility = View.GONE
            binding.tvRatingOwner.visibility = View.GONE
        }
    }

    private fun setDescription() {
        if (movie?.description != null) {
            binding.tvDescription.text = movie?.description
        }
    }

    private fun setOnBackClickListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    companion object {
        const val NAME = "MovieDetailsFragment"
        private const val ARG_MOVIE = "movie"

        @JvmStatic
        fun newInstance(movie: Movie) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE, movie)
                }
            }
    }
}