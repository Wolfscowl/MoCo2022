package com.example.greenpoint.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenpoint.base.AuthenticationState
import com.example.greenpoint.base.BaseViewModel
import com.example.greenpoint.data.User
import com.example.greenpoint.firebase.FireBaseClass
//import com.example.greenpoint.firebase.getUserInfo2
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {


    /** --------------------------------------- LIVE-DATA -----------------------------------------*/
    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> = _userInfo


    /** ==================================== getUserInfo ======================================== */
    fun getUserInfo() {
        if (authenticationState.value == AuthenticationState.AUTHENTICATED)
            FireBaseClass().getUserInfo{ user -> _userInfo.value = user }
    }


    /** ==================================== getUserInfo2 ======================================== */
    // keine Relevanz - Nur zu Testzwecken auf eine andere Art implementiert
//    fun getUserInfo2() {
//        viewModelScope.launch {
//            getUserInfo2(FireBaseClass().getCurrentUserID()).collect { user ->
//                if (user!=null)
//                    _userInfo.value = user
//            }
//        }
//    }

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "HomeViewModel"
    }
}