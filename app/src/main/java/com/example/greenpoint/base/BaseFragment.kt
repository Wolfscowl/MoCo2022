package com.example.greenpoint.base

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.greenpoint.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


open class BaseFragment: Fragment() {

    private lateinit var progressDialog: Dialog


    /** ===================================== getColorfulAppName  ================================ */
    fun getColorfulAppName(firstColorID: Int, secondColorID: Int) : SpannableString {
        val spannableString = SpannableString( resources.getString(R.string.app_name) )
        val black = ForegroundColorSpan(ContextCompat.getColor(this.requireContext(), firstColorID))
        val green = ForegroundColorSpan(ContextCompat.getColor(this.requireContext(), secondColorID))
        spannableString.setSpan(black,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(green,5,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }


    /** ======================================== hideSystemBar  ================================== */
    fun hideSystemBar() {
        if (Build.VERSION.SDK_INT < 30) {
            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        else {
            requireActivity().window.setDecorFitsSystemWindows(false)
            val controller = requireActivity().window.insetsController
            if (controller != null) {
                //controller.hide(WindowInsets.Type.navigationBars())
                controller.hide(WindowInsets.Type.statusBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    /** ===================================== showProgressDialog  ================================ */
    fun showProgressDialog(text: String) {
        progressDialog = Dialog(this.requireContext())
        /*Screen-Inhalt aus XML-Datei setzen.*/
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.findViewById<TextView>(R.id.tv_progress_text).text = text
        // Start des Dialogs.
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    /** ==================================== hideProgressDialog  ================================= */
    fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    /** ======================================= showDialog  ======================================= */
    fun showDialog(title: String, message: String, button1: String, fun1: ()->Unit, button2: String, fun2: ()->Unit ) {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setNegativeButton(button1)
            { _, _ -> fun1() }
            .setPositiveButton(button2)
            { _, _ -> fun2() }
            .show()
    }


    /** ======================================= showSnackBar ===================================== */
    fun showSnackBar(message: String, success: Boolean) {
        val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        val bgColor: Int
        val textView = snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(this.requireContext(), R.color.white))))

        if (success)
            bgColor = ContextCompat.getColor(this.requireContext(), R.color.success_color)
        else
            bgColor = ContextCompat.getColor(this.requireContext(), R.color.error_color)

        snackBarView.setBackgroundColor(bgColor)
        snackBar.show()
    }


    /** ============================ afterTextChangedValidate =====================================*/
    fun EditText.afterTextChangedValidate(afterTextChanged: () -> Boolean, action1: ()->Unit, action2: ()->Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) {
                if (afterTextChanged())
                    action1()
                else
                    action2()
            }
        })
    }
    /** ============================ afterTextChangedAction =====================================*/
    fun EditText.afterTextChangedAction(action: ()->Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) { action() }
        })
    }


    /** ================================= onFocusChanged ========================================*/
    fun EditText.onFocusChanged(onFocusChanged: () -> Boolean, action1: ()->Unit, action2: ()->Unit) {
        this.setOnFocusChangeListener{ _, hasFocus ->
               if (!hasFocus) {
                   if (onFocusChanged())
                       action1()
                   else
                       action2()
               }
        }
    }


}