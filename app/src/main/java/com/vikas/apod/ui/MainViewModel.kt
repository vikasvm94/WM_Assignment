package com.vikas.apod.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vikas.apod.model.APODResponse
import com.vikas.apod.repository.Repository

class MainViewModel : ViewModel() {

    private lateinit var apodLiveData: LiveData<APODResponse>

    fun getAPOD(): LiveData<APODResponse> {
        apodLiveData = Repository.getAPOD()
        return apodLiveData
    }
}