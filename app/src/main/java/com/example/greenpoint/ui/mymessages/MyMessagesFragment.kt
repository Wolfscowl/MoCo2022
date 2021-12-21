package com.example.greenpoint.ui.mymessages

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
import com.example.greenpoint.R
import com.example.greenpoint.base.AuthenticationState
import com.example.greenpoint.base.BaseFragment
import com.example.greenpoint.databinding.FragmentMyMessagesBinding


class MyMessagesFragment : BaseFragment() {

    private lateinit var myMessagesViewModel: MyMessagesViewModel
    private var _binding: FragmentMyMessagesBinding? = null
    private val binding get() = _binding!!

    /** =================================== onCreateView ========================================= */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myMessagesViewModel = ViewModelProvider(this).get(MyMessagesViewModel::class.java)

        _binding = FragmentMyMessagesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /** ---------------------------------- observer ------------------------------------------*/
        val textView: TextView = binding.textMyMessages
        myMessagesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        myMessagesViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
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
        private const val TAG = "MyMessagesFragment"
    }
}