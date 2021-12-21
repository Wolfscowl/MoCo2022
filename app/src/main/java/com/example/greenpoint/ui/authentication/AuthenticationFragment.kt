package com.example.greenpoint.ui.authentication


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.greenpoint.R
import com.example.greenpoint.databinding.FragmentAuthenticationBinding
import com.example.greenpoint.base.BaseFragment

class AuthenticationFragment : BaseFragment() {

    private lateinit var authenticationViewModel: AuthenticationViewModel
    private var _binding: FragmentAuthenticationBinding? = null
    private val binding get() = _binding!!

    /** =================================== onCreateView ========================================= */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Textview AppName einfärben und setzen
        binding.tvAppName.text = getColorfulAppName(R.color.black, R.color.green)

        /** --------------------------------- OnClickListener -------------------------------------*/
        // Zu SignUp Fragment navigieren
        binding.btnSignUp.setOnClickListener {
            val action = AuthenticationFragmentDirections.actionAuthenticationFragmentToSignUpFragment()
            view?.findNavController()?.navigate(action)
        }

        // Zu SignIn Fragment navigieren
        binding.btnSignIn.setOnClickListener {
            val action = AuthenticationFragmentDirections.actionAuthenticationFragmentToSignInFragment()
            view?.findNavController()?.navigate(action)
        }

        return root
    }


    /** =================================== onDestroyView ========================================= */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "AuthenticationFragment"
    }

}