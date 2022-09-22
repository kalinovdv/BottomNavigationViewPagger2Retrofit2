package com.example.bottomnavigationviewpagger2retrofit2.mars.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import com.example.bottomnavigationviewpagger2retrofit2.databinding.FragmentMarsBinding

class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
           binding.fragmentMarsTextViewDate.text = it.getString("date")
           binding.fragmentMarsImageView.load(it.getString("image"))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarsFragment()
    }
}