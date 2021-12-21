package com.example.greenpoint.ui.placead

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenpoint.AuthenticationActivity
import com.example.greenpoint.base.AuthenticationState
import com.example.greenpoint.base.BaseFragment
import com.example.greenpoint.databinding.FragmentPlaceAdBinding
import com.example.greenpoint.ui.authentication.AuthenticationFragment


class PlaceAdFragment : BaseFragment() {

    private lateinit var placeAdViewModel: PlaceAdViewModel
    private var _binding: FragmentPlaceAdBinding? = null
    private val binding get() = _binding!!





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        placeAdViewModel = ViewModelProvider(this).get(PlaceAdViewModel::class.java)

        if ( placeAdViewModel.authenticationState.value == AuthenticationState.UNAUTHENTICATED) {
            val intent = Intent(this.requireContext(), AuthenticationActivity::class.java)
            this.requireContext().startActivity(intent)
            activity?.finish()
        }

    }


    /** =================================== onCreateView ========================================= */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        placeAdViewModel = ViewModelProvider(this).get(PlaceAdViewModel::class.java)

        _binding = FragmentPlaceAdBinding.inflate(inflater, container, false)
        val root: View = binding.root


        /** ---------------------------------- observer ------------------------------------------*/
        placeAdViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            if (it == AuthenticationState.UNAUTHENTICATED) {
                // Wenn nicht eingeloggt AuthenticationActivity starten
                val intent = Intent(this.requireContext(), AuthenticationActivity::class.java)
                this.requireContext().startActivity(intent)
                activity?.finish()
            }
        })

        val textView: TextView = binding.textPlaceAd
        placeAdViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
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
        private const val TAG = "PlaceAdFragment"
    }
}