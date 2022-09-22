package com.example.bottomnavigationviewpagger2retrofit2.earth.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import com.example.bottomnavigationviewpagger2retrofit2.databinding.FragmentEarthBinding

class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    private val baseUrlEarthPictures = "https://epic.gsfc.nasa.gov/archive/natural/"
    private val extensionEarthPictures = ".png"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val dateEarthData = it.getString("date")!!
            val date = dateEarthData.substringBefore(" ")
            val year = date.substringBefore("-")
            val day = date.substringAfterLast("-")
            val beforeLast = date.substringBeforeLast("-")
            val month = beforeLast.substringAfter("-")
            //https://epic.gsfc.nasa.gov/archive/natural/2015/10/31/png/epic_1b_20151031074844.png
            val url = baseUrlEarthPictures + year + "/" + month + "/" + day + "/png/" + it.getString("image") + extensionEarthPictures
            binding.fragmentEarthImageView.load(url)
            binding.fragmentEarthTextViewDate.text = it.getString("date")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = EarthFragment()
    }
}