package com.example.bottomnavigationviewpagger2retrofit2.sun.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomnavigationviewpagger2retrofit2.R
import com.example.bottomnavigationviewpagger2retrofit2.databinding.FragmentSunBinding

class SunFragment : Fragment() {

    private var _binding: FragmentSunBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            when (it.getInt("type")) {
                0 -> {
                    binding.fragmentSunTextViewError.text = "Нет данных"
                }
                1 -> {
                    binding.fragmentSunTextViewStartDateVal.text = it.getString("startDate")
                    binding.fragmentSunTextViewPeakDateVal.text = it.getString("peakDate")
                    binding.fragmentSunTextViewEndDateVal.text = it.getString("endDate")
                    binding.fragmentSunTextViewClassTypeVal.text = it.getString("classType")
                    binding.fragmentSunTextViewSourceLocationVal.text = it.getString("sourceLocation")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
         @JvmStatic
        fun newInstance() = SunFragment()
    }
}