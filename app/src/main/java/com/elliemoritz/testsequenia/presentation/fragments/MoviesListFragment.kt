package com.elliemoritz.testsequenia.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.elliemoritz.testsequenia.R
import com.elliemoritz.testsequenia.databinding.FragmentMoviesListBinding
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding: FragmentMoviesListBinding
        get() = _binding ?: throw RuntimeException("FragmentMoviesListBinding == null")

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
        showSnackbar()
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
                Log.d("MainActivity", "Repeat operation")
            }

        val snackbarView = snackbar.view
        snackbarView.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE)

        snackbar.show()
    }

    companion object {

        private const val PADDING_VALUE = 16

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}