package com.example.greenpoint.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.greenpoint.AuthenticationActivity
import com.example.greenpoint.R
import com.example.greenpoint.base.AuthenticationState
import com.example.greenpoint.base.BaseFragment
import com.example.greenpoint.databinding.FragmentSearchBinding
import com.example.greenpoint.databinding.FragmentSettingBinding
import com.example.greenpoint.ui.authentication.AuthenticationFragmentDirections
import com.example.greenpoint.ui.search.SearchViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SettingFragment : BaseFragment() {
    private lateinit var settingViewModel: SettingViewModel
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    /** =================================== onCreateView ========================================= */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        settingViewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /** ---------------------------------- observer ------------------------------------------*/
        settingViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            if (it == AuthenticationState.UNAUTHENTICATED) {
                binding.btnAuthentication.text = resources.getString(R.string.sign_in_button)
                binding.btnChangePassword.visibility = View.GONE
                binding.btnEditProfil.visibility = View.GONE
            }
            else {
                binding.btnAuthentication.text = resources.getString(R.string.sign_out_button)
                binding.btnChangePassword.visibility = View.VISIBLE
                binding.btnEditProfil.visibility = View.VISIBLE
            }
        })

        /** --------------------------------- OnClickListener -------------------------------------*/
        binding.btnAuthentication.setOnClickListener {
            if (settingViewModel.authenticationState.value == AuthenticationState.AUTHENTICATED) {
                showDialog(
                    getString(R.string.sign_out_title),
                    getString(R.string.sign_out_message),
                    getString(R.string.sign_out_no),
                    {Unit},
                    getString(R.string.sign_out_yes),
                    {settingViewModel.signOut()}
                )
            }
            else {
                val intent = Intent(this.requireContext(), AuthenticationActivity::class.java)
                this.requireContext().startActivity(intent)
                activity?.finish()
            }
        }

        binding.btnChangePassword.setOnClickListener {
            val action = SettingFragmentDirections.actionNavSettingsToChangePasswordFragment()
            view?.findNavController()?.navigate(action)
        }



        return root
    }

    /** =================================== onDestroyView ========================================*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "SettingFragment"
    }
}