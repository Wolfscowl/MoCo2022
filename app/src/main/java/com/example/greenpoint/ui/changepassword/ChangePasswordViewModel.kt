package com.example.greenpoint.ui.changepassword

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greenpoint.base.*
import com.example.greenpoint.firebase.FireBaseClass
import com.google.firebase.auth.EmailAuthProvider
import java.lang.Exception

class ChangePasswordViewModel : BaseViewModel() {


    /** --------------------------------------- LIVE-DATA -----------------------------------------*/
    private val _changePasswordState = MutableLiveData<ChangePasswordState>().apply {
        value = ChangePasswordState.NONE
    }
    val changePasswordState: LiveData<ChangePasswordState> = _changePasswordState

    private val _progressState = MutableLiveData<ProgressState>().apply {
        value = ProgressState.NONE
    }
    val progressState: LiveData<ProgressState> = _progressState



    /** ====================================== validateForm =======================================*/
    fun validateForm(newPassword: String, newPasswordCoinfrimation: String): Boolean =
        isValidPassword(newPassword) && isPasswordConfirmed(newPassword,newPasswordCoinfrimation)


    /** ================================== isPasswordConfirmed =================================== */
    fun isPasswordConfirmed(password: String, passwordConfirmation: String) = password == passwordConfirmation


    /** ====================================== isValidPassword ====================================*/
    fun isValidPassword(password: String) : Boolean {
        if (TextUtils.isEmpty(password))
            return false
        else
            return password.length >= 8 && !password.contains(' ')
    }


    /** ================================== changePassword ======================================== */
    fun changePassword(currentPassword: String, newPassword: String, newPasswordConfirmation: String) {
        if (authenticationState.value == AuthenticationState.AUTHENTICATED && validateForm(newPassword, newPasswordConfirmation)) {
            _progressState.value = ProgressState.IN_PROGRESS
            FireBaseClass().reauthenticate(
                currentPassword,
                {FireBaseClass().updatePassword(newPassword,::updatePasswordSuccess, ::updatePasswordFailure)},
                ::reAuthFailure
            )
        }
    }


    /** ================================== updatePassword ======================================== */
    fun updatePassword(newPassword: String) {
        val user = auth.currentUser!!
        user.updatePassword(newPassword).addOnCompleteListener { task ->
            _progressState.value = ProgressState.DONE
            if (task.isSuccessful) {
                Log.d(TAG, "password updated:success")
                _changePasswordState.value = ChangePasswordState.SUCCESS
            } else {
                _changePasswordState.value = ChangePasswordState.UNSUCCESS
                Log.d(TAG, "password updated:failure" + " " + task.exception)
            }
        }
    }


    /** =============================== UpdatePasswordSuccess =====================================*/
    fun updatePasswordSuccess() {
        _progressState.value = ProgressState.DONE
        _changePasswordState.value = ChangePasswordState.SUCCESS
    }


    /** ================================= UpdatePasswordFailure ===================================*/
    fun updatePasswordFailure(e: Exception) {
        _progressState.value = ProgressState.DONE
        try {
            throw e
        }
        catch(e: Exception) {
            Log.d(TAG, "SignUp:failure", e)
            _changePasswordState.value = ChangePasswordState.UNSUCCESS
        }
    }


    /** ================================= reAuthFailure ===================================*/
    fun reAuthFailure(e: Exception) {
        _progressState.value = ProgressState.DONE
        try {
            throw e
        }
        catch(e: Exception) {
            Log.d(TAG, "SignUp:failure", e)
            _changePasswordState.value = ChangePasswordState.INVALID
        }
    }

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "ChangePasswordViewModel"
    }

}