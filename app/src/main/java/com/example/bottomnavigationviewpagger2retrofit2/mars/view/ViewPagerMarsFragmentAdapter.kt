package com.example.bottomnavigationviewpagger2retrofit2.mars.view

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerMarsFragmentAdapter(
    activity: FragmentActivity,
    urlEarthList: ArrayList<String>,
    dateEarthList: ArrayList<String>
) : FragmentStateAdapter(activity) {

    private var urlList = urlEarthList
    private var dateList = dateEarthList

    override fun createFragment(position: Int): Fragment = MarsFragment().apply {
        arguments = bundleOf(
            "date" to dateList[position],
            "image" to urlList[position]
        )
    }

    override fun getItemCount(): Int = urlList.size

}