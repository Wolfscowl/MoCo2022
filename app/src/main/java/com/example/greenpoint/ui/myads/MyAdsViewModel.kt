package com.example.greenpoint.ui.myads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenpoint.base.BaseViewModel

class MyAdsViewModel : BaseViewModel() {

    /** --------------------------------------- LIVE-DATA -----------------------------------------*/
    private val _text = MutableLiveData<String>().apply {
        value = "This is place ad Fragment"
    }
    val text: LiveData<String> = _text


    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "MyAdsViewModel"
    }
}