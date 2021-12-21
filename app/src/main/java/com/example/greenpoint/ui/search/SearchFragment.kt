package com.example.greenpoint.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenpoint.base.BaseFragment
import com.example.greenpoint.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    /** =================================== onCreateView ========================================= */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /** ---------------------------------- observer ------------------------------------------*/
        val textView: TextView = binding.textSearch
        searchViewModel.text.observe(viewLifecycleOwner, Observer {
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
        private const val TAG = "SearchFragment"
    }
}