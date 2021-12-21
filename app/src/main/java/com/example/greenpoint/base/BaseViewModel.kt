package com.example.greenpoint.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.greenpoint.firebase.FireBaseUserLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

open class BaseViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

//    // TODO -> Überpüfen
//    val user1: LiveData<FirebaseUser?> = FireBaseUserLiveData()
//
//
//    // TODO -> Überpüfen
//    val user2 = FireBaseUserLiveData().map { user ->
//        if (user != null) {
//            Log.d("BaseViewModel - user", "user NOT NULL")
//            user
//        } else {
//            Log.d("BaseViewModel - auth", "user NULL")
//            null
//        }
//    }
    /** --------------------------------------- LIVE-DATA -----------------------------------------*/
    val authenticationState = FireBaseUserLiveData().map { user ->
        if (user != null) {
            Log.d("BaseViewModel - auth", "user NOT NULL")
            AuthenticationState.AUTHENTICATED
        } else {
            Log.d("BaseViewModel - auth", "user NULL")
            AuthenticationState.UNAUTHENTICATED
        }
    }

}