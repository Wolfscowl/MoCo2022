package com.example.greenpoint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.greenpoint.ui.authentication.AuthenticationFragment
import com.google.android.material.internal.ContextUtils.getActivity


class AuthenticationActivity : AppCompatActivity() {
    private lateinit var navController: NavController


    /** ====================================== onCreate ===========================================*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        // Verweis auf das fragment der activity_authentication.xml
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Instanzieren des navController für das NavHostFragment
        navController = navHostFragment.navController

        // Stellt sich das die ActionBar interkationen an den NavController weitergegeben werden
        setupActionBarWithNavController(navController)
    }


    /** ============================== onSupportNavigateUp ========================================
     *  Enables back button support. Simply navigates one element up on the stack.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



    /** ===================================== onBackPressed ========================================
     *  startet die MainActivity neu wenn das aktuelle Fragment das AuthenticationFragment ist
     */
    override fun onBackPressed() {
        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = hostFragment!!.childFragmentManager.fragments[0]
        // Wenn das aktuelle Fragment das AuthenticationFragment ist, dann MainActivity neustarten
        if (currentFragment != null && currentFragment is AuthenticationFragment) {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            finish()
        }
        else
            super.onBackPressed()
    }


}