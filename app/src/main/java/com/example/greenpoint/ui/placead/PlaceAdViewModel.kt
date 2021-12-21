package com.example.greenpoint.ui.placead

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.greenpoint.base.AuthenticationState
import com.example.greenpoint.base.BaseViewModel
import com.example.greenpoint.firebase.FireBaseUserLiveData

class PlaceAdViewModel : BaseViewModel() {

    /** --------------------------------------- LIVE-DATA -----------------------------------------*/
    private val _text = MutableLiveData<String>().apply {
        value = "This is place ad Fragment"
    }
    val text: LiveData<String> = _text


    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "PlaceAdViewModel"
    }
}