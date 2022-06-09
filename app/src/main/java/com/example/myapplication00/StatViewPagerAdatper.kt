package com.example.myapplication00

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class StatViewPagerAdatper(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0->StatFragmentDay()
            1->StatFragmentType()
            else->StatFragmentKcal()
        }
    }
}