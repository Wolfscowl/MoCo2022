package com.example.greenpoint.ui.setting

import androidx.lifecycle.map
import com.example.greenpoint.base.AuthenticationState
import com.example.greenpoint.base.BaseViewModel
import com.example.greenpoint.firebase.FireBaseClass
import com.example.greenpoint.firebase.FireBaseUserLiveData
import com.google.firebase.auth.FirebaseAuth

class SettingViewModel : BaseViewModel() {

    /** --------------------------------------- LIVE-DATA -----------------------------------------*/


    /** ====================================== signOut =========================================== */
    fun signOut() {
        FireBaseClass().signOut()
    }

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "SettingViewModel"
    }
}