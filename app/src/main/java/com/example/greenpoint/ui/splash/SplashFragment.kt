package com.example.greenpoint.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greenpoint.R
import com.example.greenpoint.databinding.FragmentSplashBinding
import com.example.greenpoint.base.BaseFragment

class SplashFragment : BaseFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    /** ==================================== onCreateView ======================================== */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // In den FUllScreen Modus wechseln
        //hideSystemBar()

        // Textview AppName einfärben und setzen
        binding.tvAppName.text = getColorfulAppName(R.color.white, R.color.green)

        return root
    }

    /** ==================================== onDestroyView ======================================= */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}