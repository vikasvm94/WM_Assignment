package com.vikas.apod.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vikas.apod.R
import com.vikas.apod.model.APODResponse
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A main activity which is responsible for showing the astronomy picture of the day along with details
 * If the internet is available it displays the current day picture else it displays previous day picture if available.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    /**
     * A observer for receiving astronomy picture of the day response from API.
     */
    private val apodObserver = Observer<APODResponse> {

        if (it != null && it.errorState == null) {

            Glide.with(this).load(it.hdImageUrl).into(image)
            detailTextView.text = it.explanation
            imageTitle.text = it.title
            errorText.isVisible = it.isCached
        } else {
            showErrorMessage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getAPOD(this).observe(this, apodObserver)

    }

    /**
     * A method which displays error message when there is no data available
     */
    private fun showErrorMessage() {
        scrollView.isVisible = false
        errorText.text = getString(R.string.general_error)
        errorText.isVisible = true
    }

}