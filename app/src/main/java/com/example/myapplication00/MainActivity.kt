package com.example.myapplication00

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.myapplication00.databinding.ActivityMainBinding
import com.example.myapplication00.databinding.ActivityRegisterBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var myDBHelper: DBHelper
    @RequiresApi(Build.VERSION_CODES.O)
    lateinit var binding: ActivityMainBinding
    val textarr = arrayListOf<String>("캘린더", "통계", "설정")
    val icontarr = arrayListOf<Int>(R.drawable.ic_navbar_calendar,
                                                R.drawable.ic_navbar_statistic,
                                                R.drawable.ic_navbar_settings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initlayout()
        initDB()
    }

    private fun initlayout() {
        binding.viewpager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewpager){
            tab, position ->
                tab. text = textarr[position]
                tab.setIcon(icontarr[position])
        }.attach()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initDB(){
        val dbfile = getDatabasePath("project.db")
        //데이터베이스 폴더가 존재하지 않는 경우 실행하는 함수
        if(!dbfile.parentFile.exists()){
            dbfile.parentFile.mkdir()
        }

        myDBHelper = DBHelper(this)
        val date = LocalDate.now().toString()
        myDBHelper.findGoal(date)
    }
}