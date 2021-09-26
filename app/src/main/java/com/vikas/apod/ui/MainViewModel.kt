package com.vikas.apod.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vikas.apod.model.APODResponse
import com.vikas.apod.repository.Repository

/**
 * A viewModel acts as a mediator between activity and repository
 */
class MainViewModel : ViewModel() {


    private lateinit var apodLiveData: LiveData<APODResponse>

    /**
     * Use this method to get astronomy picture of the day and details fro NASA
     */
    fun getAPOD(context: Context): LiveData<APODResponse> {
        apodLiveData = Repository.getAPOD(context)
        return apodLiveData
    }
}