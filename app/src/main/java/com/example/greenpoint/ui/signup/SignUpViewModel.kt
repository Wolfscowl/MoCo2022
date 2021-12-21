package com.example.greenpoint.ui.signup


import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greenpoint.base.BaseViewModel
import com.example.greenpoint.base.ProgressState
import com.example.greenpoint.base.SignUpState
import com.example.greenpoint.data.User
import com.example.greenpoint.firebase.FireBaseClass
import com.example.greenpoint.utils.Constants
import com.google.firebase.auth.FirebaseAuthUserCollisionException

import java.lang.Exception


class SignUpViewModel : BaseViewModel() {


    /** --------------------------------------- LIVE-DATA -----------------------------------------*/
    private val _signUpState = MutableLiveData<SignUpState>().apply {
        value = SignUpState.NONE
    }
    val signUpState: LiveData<SignUpState> = _signUpState


    private val _progressState = MutableLiveData<ProgressState>().apply {
        value = ProgressState.NONE
    }
    val progressState: LiveData<ProgressState> = _progressState



    /** ====================================== validateForm =======================================*/
    fun validateForm(name: String, email: String, password: String, passwordConfirmation: String): Boolean =
        isValidName(name) && isValidEmail(email) && isValidPassword(password) && isPasswordConfirmed(password, passwordConfirmation)


    /** ====================================== isValidName =======================================*/
    fun isValidName(name: String) : Boolean {
        if (TextUtils.isEmpty(name))
            return false;
        else
            return name.length >= 4 && !name.contains(' ')
    }

    /** ====================================== isValidEmail =======================================*/
    fun isValidEmail(email: String) : Boolean {
        if (TextUtils.isEmpty(email))
            return false;
        else
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /** ====================================== isValidPassword ====================================*/
    fun isValidPassword(password: String) : Boolean {
        if (TextUtils.isEmpty(password))
            return false
        else
            return password.length >= 8 && !password.contains(' ')
    }

    /** ================================== isPasswordConfirmed =================================== */
    fun isPasswordConfirmed(password: String, passwordConfirmation: String) = password == passwordConfirmation


    /** ========================================= signUp ==========================================*/
    fun signUp(name: String, email: String, password: String, passwordConfirmation: String) {

        if (validateForm(name,email,password,passwordConfirmation)) {
            _progressState.value = ProgressState.IN_PROGRESS
            val user = User(name = name, email = email, credits = Constants.START_CREDITS)
            FireBaseClass().signUp(user,password,::signUpSuccess,::signUpFailure)
        }

    }


    /** ====================================== signUpSuccess =======================================*/
    fun signUpSuccess() {
        Log.d(TAG, "signIn:success")
        _progressState.value = ProgressState.DONE
        _signUpState.value = SignUpState.SUCCESS
    }


    /** ====================================== signUpFailure =======================================*/
    fun signUpFailure(e: Exception) {
        _progressState.value = ProgressState.DONE
        try {
            throw e
        } catch (e: FirebaseAuthUserCollisionException) {
            Log.d(TAG, "SignUp:failure", e)
            _signUpState.value = SignUpState.USER_ALREADY_EXIST
        } catch (e: Exception) {
            Log.d(TAG, "SignUp:failure", e)
            _signUpState.value = SignUpState.FAILURE
        }
    }


    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "SignUpModel"
    }

}