package com.vikas.apod.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vikas.apod.ACTION_NETWORK_CHANGE
import com.vikas.apod.R
import com.vikas.apod.model.APODResponse
import com.vikas.apod.repository.NetworkUtilities
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


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
        getDate()
        getAstronomyPictureOfTheDay()
    }

    override fun onStart() {
        super.onStart()
        //Registering for connectivity change
        val intentFilter = IntentFilter(ACTION_NETWORK_CHANGE)
        registerReceiver(networkReceiver, intentFilter)
    }


    override fun onStop() {
        super.onStop()
        //Unregistering for coonectivty change
        unregisterReceiver(networkReceiver)
    }

    /**
     * A method to fetch astronomy picture of the day from NASA
     */
    private fun getAstronomyPictureOfTheDay() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getAPOD(this).observe(this, apodObserver)
    }


    /**
     * A method to diaplay current date on teh toolbar
     */
    private fun getDate() {
        val c: Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault())
        val df = SimpleDateFormat("dd/MM/yyyy")
        val currentDate: String = df.format(c.getTime())

        supportActionBar?.title = "${getString(R.string.app_name)}       $currentDate"
    }

    /**
     * A method which displays error message when there is no data available
     */
    private fun showErrorMessage() {
        scrollView.isVisible = false
        errorText.text = getString(R.string.general_error)
        errorText.isVisible = true
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                if (it.action == ACTION_NETWORK_CHANGE) {
                    if (NetworkUtilities.isNetworkConnected(applicationContext)) {
                        getAstronomyPictureOfTheDay()
                    }
                }
            }
        }

    }

}