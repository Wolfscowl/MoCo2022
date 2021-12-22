package com.example.greenpoint.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenpoint.base.AuthenticationState
import com.example.greenpoint.base.BaseFragment
import com.example.greenpoint.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    /** =================================== onCreateView ========================================= */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /** ---------------------------------- observer ------------------------------------------*/
        homeViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            if (it == AuthenticationState.AUTHENTICATED) {
                homeViewModel.getUserInfo()
            }
        })

        homeViewModel.userInfo.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.textHome.text = it.name
            }
        })

        return root
    }

    /** ==================================== onDestroyView ======================================= */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** ================================== companion object ======================================= */
    companion object {
        private const val TAG = "HomeFragment"
    }
}