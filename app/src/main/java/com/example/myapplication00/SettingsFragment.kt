package com.example.myapplication00

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SettingsFragment : Fragment() {

    var menuData = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        initData()

        val recyclerView = view.findViewById<RecyclerView>(R.id.settingRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SettingsAdapter(menuData)

        return view
    }

    private fun initData() {
        menuData.add("다크모드")
        menuData.add("앱 초기화")
        menuData.add("목표 수정")
        menuData.add("도움말")
    }

}