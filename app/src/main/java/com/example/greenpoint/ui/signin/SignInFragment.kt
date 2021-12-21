package com.example.greenpoint.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenpoint.MainActivity
import com.example.greenpoint.R
import com.example.greenpoint.databinding.FragmentSignInBinding
import com.example.greenpoint.base.BaseFragment
import com.example.greenpoint.base.ProgressState
import com.example.greenpoint.base.SignInState


class SignInFragment : BaseFragment() {
    private lateinit var signInViewModel: SignInViewModel
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val dTag = "SignInFragment"

    /** =================================== onCreateView ========================================= */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Textview AppName einfärben und setzen
        binding.tvAppName.text = getColorfulAppName(R.color.black, R.color.green)


        /** ---------------------------------- observer ------------------------------------------*/
        signInViewModel.signInState.observe(viewLifecycleOwner, Observer {
            if(it == SignInState.SUCCESS) {
                val intent = Intent(this.requireContext(), MainActivity::class.java)
                this.requireContext().startActivity(intent)
                activity?.finish()
            }
            if(it == SignInState.FAILURE)
                showSnackBar(resources.getString(R.string.sign_in_unsuccessful),false)
        })

        signInViewModel.progressState.observe(viewLifecycleOwner, Observer {
            if(it == ProgressState.IN_PROGRESS)
                showProgressDialog(resources.getString(R.string.please_wait))
            if(it == ProgressState.DONE)
                hideProgressDialog()
        })


        /** --------------------------------- OnClickListener -------------------------------------*/
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            showErroInputs(email,password)

            if (signInViewModel.validateForm(email,password))
                signInViewModel.signIn(email, password)
        }
        return root
    }

    /** =================================== showErroInputs ========================================*/
    fun showErroInputs(email: String,password: String) {
        binding.apply {
            if (!signInViewModel.isValidEmail(email)) {
                etEmailMaterial.isErrorEnabled = true
                etEmailMaterial.error = getString(R.string.sign_in_email_error)
            }
            else
                etEmailMaterial.isErrorEnabled = false

            if (!signInViewModel.isValidPassword(password)) {
                etPasswordMaterial.isErrorEnabled = true
                etPasswordMaterial.error = getString(R.string.sign_in_password_error)
            }
            else
                etPasswordMaterial.isErrorEnabled = false
        }
    }

    /** ==================================== onDestroyView ======================================= */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "SignInFragment"
    }

}