package com.example.greenpoint.ui.signin

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.greenpoint.base.BaseViewModel
import com.example.greenpoint.base.ProgressState
import com.example.greenpoint.base.SignInState
import com.example.greenpoint.data.User
import com.example.greenpoint.firebase.FireBaseClass
import kotlinx.coroutines.launch
import java.lang.Exception

class SignInViewModel : BaseViewModel() {

    /** --------------------------------------- LIVE-DATA -----------------------------------------*/
    private val _signInState = MutableLiveData<SignInState>().apply {
        value = SignInState.NONE
    }
    val signInState: LiveData<SignInState> = _signInState

    private val _progressState = MutableLiveData<ProgressState>().apply {
        value = ProgressState.NONE
    }
    val progressState: LiveData<ProgressState> = _progressState


    /** ====================================== validateForm =======================================*/
    fun validateForm(email: String, password: String): Boolean =
        isValidEmail(email) && isValidPassword(password)


    /** ====================================== isValidEmail =======================================*/
    fun isValidEmail(email: String) : Boolean {
        if (TextUtils.isEmpty(email))
            return false;
        else
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    /** ====================================== isValidPassword ====================================*/
    fun isValidPassword(password: String) : Boolean {
        if (TextUtils.isEmpty(password))
            return false
        else
            return password.length >= 8 && !password.contains(' ')
    }


    /** ========================================= signIn ==========================================*/
    fun signIn(email: String, password: String) {
        if (validateForm(email, password)) {
            _progressState.value = ProgressState.IN_PROGRESS
            FireBaseClass().signIn(email,password,::signInSuccess,::signInFailure)
        }
        //val test = FireBaseClass().getUserInfo2(FireBaseClass().getCurrentUserID())
        //Log.d(dTag, "UserInfo2: " + test.toString())
    }


    /** ====================================== signInSuccess =======================================*/
    fun signInSuccess() {
        _progressState.value = ProgressState.DONE
        _signInState.value = SignInState.SUCCESS
    }


    /** ====================================== signInFailure =======================================*/
    fun signInFailure(e: Exception) {
        _progressState.value = ProgressState.DONE
        try {
            throw e
        }
        catch(e: Exception) {
            Log.d(TAG, "SignUp:failure", e)
            _signInState.value = SignInState.FAILURE
            }
    }

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "SignInViewModel"
    }

}