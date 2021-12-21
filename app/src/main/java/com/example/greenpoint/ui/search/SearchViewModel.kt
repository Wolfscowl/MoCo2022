package com.example.greenpoint.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenpoint.base.BaseViewModel

class SearchViewModel : BaseViewModel() {

    /** --------------------------------------- LIVE-DATA -----------------------------------------*/
    private val _text = MutableLiveData<String>().apply {
        value = "This is search Fragment"
    }
    val text: LiveData<String> = _text


    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "SearchViewModel"
    }
}