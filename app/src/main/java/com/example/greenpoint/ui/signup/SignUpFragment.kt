package com.example.greenpoint.ui.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.greenpoint.R
import com.example.greenpoint.base.AuthenticationState
import com.example.greenpoint.databinding.FragmentSignUpBinding
import com.example.greenpoint.base.BaseFragment
import com.example.greenpoint.base.ProgressState
import com.example.greenpoint.base.SignUpState
import com.example.greenpoint.firebase.FireBaseClass


class SignUpFragment : BaseFragment() {
    private lateinit var signUpViewModel: SignUpViewModel
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    /** ==================================== onCreateView =========================================*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Textview AppName einfärben und setzen
        binding.tvAppName.text = getColorfulAppName(R.color.black, R.color.green)


        /** ---------------------------------- observer ------------------------------------------*/
        signUpViewModel.signUpState.observe(viewLifecycleOwner, Observer {
            when (it) {
                SignUpState.SUCCESS -> {
                    showSnackBar(resources.getString(R.string.sign_up_successful),true)
                    //requireActivity().supportFragmentManager.popBackStack()
                    val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                    view?.findNavController()?.navigate(action)
                }
                SignUpState.USER_ALREADY_EXIST -> {
                    binding.etEmailMaterial.isErrorEnabled = true
                    binding.etEmailMaterial.error = getString(R.string.sign_up_user_error)
                }
                SignUpState.FAILURE -> {
                    showSnackBar(resources.getString(R.string.sign_up_unsuccessful),false)
                }
            }
        })

        signUpViewModel.progressState.observe(viewLifecycleOwner, Observer {
            if(it == ProgressState.IN_PROGRESS)
                showProgressDialog(resources.getString(R.string.please_wait))
            if(it == ProgressState.DONE)
                hideProgressDialog()
        })

        signUpViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            if(it == AuthenticationState.AUTHENTICATED)
                Log.d(TAG, "login")
            if(it == AuthenticationState.UNAUTHENTICATED)
                Log.d(TAG, "logout")
        })



        /** --------------------------------- OnClickListener -------------------------------------*/
        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordConfirmation = binding.etPasswordConfirmation.text.toString()

            showErroInputs(name,email,password,passwordConfirmation)

            signUpViewModel.signUp(name, email, password, passwordConfirmation)
        }


        /** --------------------------------- TextChangedListener ---------------------------------*/
        binding.apply {
            etName.afterTextChangedAction { etNameMaterial.isErrorEnabled = false }
            etEmail.afterTextChangedAction { etEmailMaterial.isErrorEnabled = false }
            etPassword.afterTextChangedAction { etPasswordMaterial.isErrorEnabled = false }
            etPasswordConfirmation.afterTextChangedAction { etPasswordConfirmationMaterial.isErrorEnabled = false }
        }


        return root
    }

    /** =================================== showErroInputs ========================================*/
    fun showErroInputs(name: String, email: String, password: String, passwordConfirmation: String) {
        binding.apply {
            if (!signUpViewModel.isValidName(name) ) {
                etNameMaterial.isErrorEnabled = true
                etNameMaterial.error = getString(R.string.sign_up_name_error)
            }
            else
                etNameMaterial.isErrorEnabled = false

            if (!signUpViewModel.isValidEmail(email)) {
                etEmailMaterial.isErrorEnabled = true
                etEmailMaterial.error = getString(R.string.sign_up_email_error)
            }
            else
                etEmailMaterial.isErrorEnabled = false

            if (!signUpViewModel.isValidPassword(password)) {
                etPasswordMaterial.isErrorEnabled = true
                etPasswordMaterial.error = getString(R.string.sign_up_password_error)
            }
            else
                etPasswordMaterial.isErrorEnabled = false

            if (!signUpViewModel.isPasswordConfirmed(password,passwordConfirmation)) {
                etPasswordConfirmationMaterial.isErrorEnabled = true
                etPasswordConfirmationMaterial.error = getString(R.string.password_confirmation_error)
            }
            else
                etPasswordConfirmationMaterial.isErrorEnabled = false
        }
    }


    /** ==================================== onDestroyView ========================================*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "SignUpFragment"
    }


}