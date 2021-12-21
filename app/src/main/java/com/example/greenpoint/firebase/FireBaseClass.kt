package com.example.greenpoint.firebase

import android.util.Log
import com.example.greenpoint.base.AuthenticationState
import com.example.greenpoint.base.ChangePasswordState
import com.example.greenpoint.base.ProgressState
import com.example.greenpoint.data.User
import com.example.greenpoint.utils.Constants
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import kotlin.concurrent.thread

class FireBaseClass {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = Firebase.firestore


    /** ====================================== getCurrentUserID ===================================*/
    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }


    /** ====================================== getAuthenticationState =============================*/
    fun getAuthenticationState(): AuthenticationState {
        if (auth.currentUser != null) {
            Log.d(TAG, "auth:AUTHENTICATED")
            return AuthenticationState.AUTHENTICATED
        } else {
            Log.d(TAG, "auth:UNAUTHENTICATED")
            return AuthenticationState.UNAUTHENTICATED
        }
    }


    /** ========================================= signIn ==========================================*/
    fun signIn(email: String, password: String, action: ()->Unit, failure: (Exception)->Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signIn:success")
                action()
            } else {
                Log.d(TAG, "signIn:failure", task.exception)
                failure(task.exception!!)
            }
        }
    }


    /** ========================================= signOut ==========================================*/
    fun signOut() {
        auth.signOut()
    }


    /** ========================================= SignUp ==========================================*/
    fun signUp(userInfo: User, password: String, action: ()->Unit, failure: (Exception)->Unit) {
        auth.createUserWithEmailAndPassword(userInfo.email, password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signUp:success")
                userInfo.id = auth.currentUser!!.uid
                saveUserInfo(userInfo, action, failure)
                auth.signOut()
                //action()
            } else {
                Log.d(TAG, "SignUp:failure", task.exception)
                failure(task.exception!!)
            }
        }
    }

    /** ================================== reauthenticate ======================================== */
    fun reauthenticate(password: String, action: ()->Unit, failure: (Exception) -> Unit) {
        if (getAuthenticationState() == AuthenticationState.AUTHENTICATED) {
            val user = auth.currentUser!!
            val credential = EmailAuthProvider.getCredential(user.email.toString(), password)
            user.reauthenticate(credential).addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "reauthenticate:success")
                    action()
                } else {
                    Log.d(TAG, "reauthenticate:failure"+" "+ user.email.toString() + " " + password, task.exception)
                    failure(task.exception!!)
                }
            }
        }
    }


    /** ================================== updatePassword ======================================== */
    fun updatePassword(newPassword: String, action: ()->Unit, failure: (Exception) -> Unit) {
        val user = auth.currentUser!!
        user.updatePassword(newPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "password updated:success")
                action()
            } else {
                Log.d(TAG, "password updated:failure" + " " + task.exception)
                failure(task.exception!!)
            }
        }
    }


    /** ====================================== saveUserInfo =======================================*/
    fun saveUserInfo(userInfo: User, action: ()->Unit, failure: (Exception)->Unit) {
        Log.d(TAG, "firestore try to write: $userInfo")
        Log.d(TAG, Thread.currentThread().toString())
        database
            .collection(Constants.USER)
            .document(userInfo.id)
            .set(userInfo)
            .addOnSuccessListener {
                Log.d(TAG, "firestore write:success")
                action()
            }
            .addOnFailureListener {
                Log.d(TAG, "firestore write:failure")
                failure(it)
            }
    }

    /** ====================================== getUserInfo =======================================*/
    fun getUserInfo(action: (User)->Unit) {
        Log.d(TAG, "TryToRead UserInfo")
        database
            .collection(Constants.USER)
            .document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                Log.d(TAG, "firestore read:success")
                action(document.toObject(User::class.java)!!)
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "firestore read:failure", e)
            }
    }



    /** ================================ getUserInfo (ALT REALTIME DATABASE) ======================*/
//    fun saveUserInfo(userInfo: User) {
//        val database = Firebase.database.reference
//        database.child("user").child(auth.currentUser!!.uid).setValue(userInfo).addOnFailureListener { e ->
//            Log.d(TAG, "R-Database Write:failure", e)
//        }
//    }

    /** ================================ getUserInfo (ALT REALTIME DATABASE) ======================*/
//    fun getUserInfo(userID: String, action: (User)->Unit) {
//        Log.d(TAG, "TryToRead UserInfo")
//        val database = Firebase.database.reference
//        database.child("user").child(userID).get()
//            .addOnSuccessListener { data ->
//            action(data.getValue(User::class.java)!!)
//            }
//            .addOnFailureListener { e ->
//            Log.d(TAG, "R-Database read:failure", e)
//        }
//    }






    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "FireBaseClass"
    }
}











/** ====================================== getUserInfo =======================================*/
// Nur Testzwecken Sie HomeViewModel
//fun CoroutineScope.getUserInfo2(userID: String) : Flow<User?> {
//    val _userInfoFlow: MutableStateFlow<User?> = MutableStateFlow(null)
//    //var userInfo: Flow<User> = flow{}
//    //Log.d(dTag, "TryToRead UserInfo")
//    val database = Firebase.database.reference
//    database.child("user").child(userID).get()
//        .addOnSuccessListener { data ->
//            launch  {
//                _userInfoFlow.emit (data.getValue(User::class.java))
//            }
//            //Log.d(dTag, "UserInfo1: " + userInfo.toString())
//        }
//        .addOnFailureListener { e ->
//            //Log.d(dTag, "R-Database read:failure", e)
//        }
//    return  _userInfoFlow
//}


