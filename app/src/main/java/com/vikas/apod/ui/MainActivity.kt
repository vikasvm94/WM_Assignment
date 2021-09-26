package com.vikas.apod.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vikas.apod.R
import com.vikas.apod.model.APODResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private val TAG = "MainActivity"

    private val apobObserver = Observer<APODResponse> {
        if (it != null && !it.isFailure) {
            Log.e(TAG, "Success ")
            Glide.with(this).load(it.hdImageUrl).into(image)
            detailTextView.text = it.explanation

             imageTitle.text = it.title
        } else {
            Log.e(TAG, "failure ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getAPOD().observe(this, apobObserver)

    }

}