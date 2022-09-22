package com.example.bottomnavigationviewpagger2retrofit2.sun.view

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerSunFragmentAdapter(
    activity: FragmentActivity,
    startDateList: ArrayList<String>,
    peakDateList: ArrayList<String>,
    endDateList: ArrayList<String>,
    classTypeList: ArrayList<String>,
    sourceLocationList: ArrayList<String>,
) : FragmentStateAdapter(activity) {

    private var startList = startDateList
    private var peakList = peakDateList
    private var endList = endDateList
    private var classList = classTypeList
    private var sourceList = sourceLocationList

    override fun createFragment(position: Int): Fragment = SunFragment().apply {
        arguments = bundleOf(
            "startDate" to startList[position],
            "peakDate" to peakList[position],
            "endDate" to endList[position],
            "classType" to classList[position],
            "sourceLocation" to sourceList[position],
        )
    }

    override fun getItemCount(): Int = startList.size

}