package com.example.greenpoint.ui.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.greenpoint.R
import com.example.greenpoint.base.BaseFragment
import com.example.greenpoint.base.ChangePasswordState
import com.example.greenpoint.base.ProgressState
import com.example.greenpoint.databinding.FragmentChangePasswordBinding


class ChangePasswordFragment : BaseFragment() {
    private lateinit var changePasswordViewModel: ChangePasswordViewModel
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    /** =================================== onCreateView ========================================= */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        changePasswordViewModel = ViewModelProvider(this).get(ChangePasswordViewModel::class.java)
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Textview AppName einfärben und setzen
        binding.tvAppName.text = getColorfulAppName(R.color.black, R.color.green)


        /** ---------------------------------- observer ------------------------------------------*/
        changePasswordViewModel.progressState.observe(viewLifecycleOwner, Observer {
            if(it == ProgressState.IN_PROGRESS)
                showProgressDialog(resources.getString(R.string.please_wait))
            if(it == ProgressState.DONE)
                hideProgressDialog()
        })

        changePasswordViewModel.changePasswordState.observe(viewLifecycleOwner, Observer {
            if(it == ChangePasswordState.SUCCESS) {
                showSnackBar(resources.getString(R.string.change_password_successful), true)
                val action = ChangePasswordFragmentDirections.actionChangePasswordFragmentToNavSettings()
                view?.findNavController()?.navigate(action)
            }
            if(it == ChangePasswordState.UNSUCCESS)
                showSnackBar(resources.getString(R.string.change_password_unsuccessful), false)
            if(it == ChangePasswordState.INVALID) {
                binding.etCurrentPasswordMaterial.isErrorEnabled = true
                binding.etCurrentPasswordMaterial.error = getString(R.string.password_confirmation_error)
            }
        })

        changePasswordViewModel.authenticationState.observe(viewLifecycleOwner, Observer { })


        /** --------------------------------- OnClickListener -------------------------------------*/
        binding.btnChangePassword.setOnClickListener {
            val currentPassword = binding.etCurrentPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            val newPasswordConfirmation = binding.etNewPasswordConfirmation.text.toString()

            showErroInputs(newPassword,newPasswordConfirmation)

            changePasswordViewModel.changePassword(currentPassword, newPassword, newPasswordConfirmation)
        }

        /** --------------------------------- TextChangedListener ---------------------------------*/
        binding.apply {
            etCurrentPassword.afterTextChangedAction { etCurrentPasswordMaterial.isErrorEnabled = false }
            etNewPassword.afterTextChangedAction { etNewPasswordMaterial.isErrorEnabled = false }
            etNewPasswordConfirmation.afterTextChangedAction { etNewPasswordConfirmationMaterial.isErrorEnabled = false }
        }


        return root
    }



    /** =================================== showErroInputs ======================================= */
    fun showErroInputs(newPassword1: String, newPassword2: String) {
        binding.apply {
            if (!changePasswordViewModel.isValidPassword(newPassword1)) {
                etNewPasswordMaterial.isErrorEnabled = true
                etNewPasswordMaterial.error = getString(R.string.new_password_error)
            }
            else
                etNewPasswordMaterial.isErrorEnabled = false

            if (!changePasswordViewModel.isPasswordConfirmed(newPassword1,newPassword2)) {
                etNewPasswordConfirmationMaterial.isErrorEnabled = true
                etNewPasswordConfirmationMaterial.error = getString(R.string.password_confirmation_error)
            }
            else
                etNewPasswordConfirmationMaterial.isErrorEnabled = false
        }
    }


    /** =================================== onDestroyView ========================================*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "ChangePasswordFragment"
    }

}