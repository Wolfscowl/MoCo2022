package com.example.greenpoint.ui.myads

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
import com.example.greenpoint.databinding.FragmentMyAdsBinding


class MyAdsFragment : BaseFragment() {

    private lateinit var myAdsViewModel: MyAdsViewModel
    private var _binding: FragmentMyAdsBinding? = null
    private val binding get() = _binding!!

    /** =================================== onCreateView ========================================= */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myAdsViewModel = ViewModelProvider(this).get(MyAdsViewModel::class.java)

        _binding = FragmentMyAdsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /** ---------------------------------- observer ------------------------------------------*/
        val textView: TextView = binding.textMyAds
        myAdsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        myAdsViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            if (it == AuthenticationState.UNAUTHENTICATED) {
                // Wenn nicht eingeloggt AuthenticationActivity starten
                val intent = Intent(this.requireContext(), AuthenticationActivity::class.java)
                this.requireContext().startActivity(intent)
                activity?.finish()
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
        private const val TAG = "MyAdsFragment"
    }
}