package com.elliemoritz.testsequenia.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elliemoritz.testsequenia.R
import com.elliemoritz.testsequenia.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showSnackbar()
    }

    private fun showSnackbar() {
        val colorYellow = ContextCompat.getColor(this, R.color.yellow)

        val snackbar = Snackbar.make(
            binding.root,
            R.string.toast_error,
            Snackbar.LENGTH_INDEFINITE
        )
            .setActionTextColor(colorYellow)
            .setAction(R.string.toast_repeat) {
                Log.d("MainActivity", "Repeat operation")
            }
//            .show()

        val snackbarView = snackbar.view
        snackbarView.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE)

        snackbar.show()
    }

    companion object {
        private const val PADDING_VALUE = 16
    }
}