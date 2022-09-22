package com.example.bottomnavigationviewpagger2retrofit2.pod.view

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerPodFragmentAdapter(
    activity: FragmentActivity,
    urlPodList: ArrayList<String>,
    datePodList: ArrayList<String>,
    explanationPodList: ArrayList<String>
) : FragmentStateAdapter(activity) {

    private var urlList = urlPodList
    private var dateList = datePodList
    private var explanationList = explanationPodList

    override fun createFragment(position: Int): Fragment = PodFragment().apply {
        arguments = bundleOf(
            "url" to urlList[position],
            "date" to dateList[position],
            "explanation" to explanationList[position]
        )
    }

    override fun getItemCount(): Int = urlList.size
}