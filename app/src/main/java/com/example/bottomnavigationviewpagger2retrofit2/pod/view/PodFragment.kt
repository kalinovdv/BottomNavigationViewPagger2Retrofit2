package com.example.bottomnavigationviewpagger2retrofit2.pod.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.example.bottomnavigationviewpagger2retrofit2.databinding.FragmentPodBinding

class PodFragment : Fragment() {

    private var _binding: FragmentPodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding.fragmentPodImageViewPod.load(it.getString("url"))
            binding.fragmentPodTextViewDate.text = it.getString("date")
            binding.fragmentPodTextViewExplanation.text = it.getString("explanation")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PodFragment()
    }
}