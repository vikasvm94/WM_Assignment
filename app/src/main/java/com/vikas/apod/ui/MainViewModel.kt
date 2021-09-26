package com.vikas.apod.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vikas.apod.model.APODResponse
import com.vikas.apod.repository.Repository

class MainViewModel : ViewModel() {


    private lateinit var apodLiveData: LiveData<APODResponse>


    fun getAPOD(context: Context): LiveData<APODResponse> {
        apodLiveData = Repository.getAPOD(context)
        return apodLiveData
    }
}