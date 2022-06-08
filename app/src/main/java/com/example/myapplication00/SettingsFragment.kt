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
    lateinit var adapter: SettingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        initData()

        val recyclerView = view.findViewById<RecyclerView>(R.id.settingRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = SettingsAdapter(menuData)
        recyclerView.adapter = adapter

        adapter.itemClickListener = object : SettingsAdapter.ItemClickListener {
            override fun onClickItem(data: String) {
//               //...
            }
            override fun onSwitchChanged(isChecked: Boolean) {
                if(isChecked) {
                    ThemeManager.applyTheme(ThemeManager.ThemeMode.DARK)
                } else {
                    ThemeManager.applyTheme(ThemeManager.ThemeMode.LIGHT)
                }
            }
        }

        return view
    }

    private fun initData() {
        menuData.add("다크모드")
        menuData.add("앱 초기화")
        menuData.add("목표 수정")
        menuData.add("도움말")
    }

}