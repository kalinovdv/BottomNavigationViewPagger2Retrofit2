package com.example.bottomnavigationviewpagger2retrofit2.earth.view

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerEarthFragmentAdapter(
    activity: FragmentActivity,
    urlEarthList: ArrayList<String>,
    dateEarthList: ArrayList<String>
) : FragmentStateAdapter(activity) {

    private var urlList = urlEarthList
    private var dateList = dateEarthList

    override fun createFragment(position: Int): Fragment = EarthFragment().apply {
        arguments = bundleOf(
            "image" to urlList[position],
            "date" to dateList[position]
        )
    }

    override fun getItemCount(): Int = urlList.size

}