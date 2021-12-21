package com.example.greenpoint.ui.mymessages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenpoint.base.BaseViewModel

class MyMessagesViewModel : BaseViewModel() {

    /** --------------------------------------- LIVE-DATA -----------------------------------------*/
    private val _text = MutableLiveData<String>().apply {
        value = "This is my messages Fragment"
    }
    val text: LiveData<String> = _text

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "MyMessagesViewModel"
    }
}