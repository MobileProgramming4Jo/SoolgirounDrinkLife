package com.example.myapplication00

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class StatFragment : Fragment() {


    lateinit var DBHelper: DBHelper
    lateinit var activity: Activity

    val cal = Calendar.getInstance()
    val nowMonth = cal.get(Calendar.MONTH) + 1
    val now = LocalDate.now().toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DBHelper = DBHelper(activity)

        val root =  inflater.inflate(R.layout.fragment_stat, container, false)
        val adviceText = root.findViewById<TextView>(R.id.stat_advice)
        val viewpager = root.findViewById<ViewPager2>(R.id.viewpager)
        val tablayout = root.findViewById<TabLayout>(R.id.tabLayout)
        val textarr = arrayListOf<String>("일 음주량","월 주종","칼로리")
        val advicearr = arrayListOf<String>("몰랐어? 술도 살 엄청 찐다구 ~","술은 살 안쪄. 너가 찌는거야","술은 적당히 먹어야해 !"
            ,"술은 '빈칼로리'만 있어서 살만 찌게 할 수 있어","술은 맛있지도 않은데 \n 살찌게 하는건 억울하지 않아?"
            , "힉 칼로리봐 ! 이게 다 어디로 갔게?")
        viewpager.adapter = StatViewPagerAdatper(this)

        TabLayoutMediator(tablayout, viewpager) {
                tab,position ->
            tab.text = textarr[position]
        }.attach()

        //제일 많이 마신 주종 판단
        val array = DBHelper.getAllAlcohol()
        var flag = 0
        var flagIndex = 0
        var flagName = ""

        for(i:Int in 0..3) {
            if(flag<array[i]) {
                flag = array[i]
                flagIndex = i
            }
        }

        when(flagIndex) {
            0->
                flagName = "소주"
            1->
                flagName = "맥주"
            2->
                flagName = "막걸리"
            3->
                flagName = "와인"
        }


        val isDailyAchieve = DBHelper.isDailyGoalAchieved(now)
        if(isDailyAchieve==0) {
            adviceText.text = "오늘은 목표치를 초과했어요 ! 경고 !!"
        }else if(isDailyAchieve==-1) {
            adviceText.text = "목표치나 일정을 입력하지 않았어요"
        }else {
            adviceText.text = "오늘은 목표치를 지켰어요 !! 칭찬해요 !"
        }

        tablayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0-> {
                        val isDailyAchieve = DBHelper.isDailyGoalAchieved(now)
                        if(isDailyAchieve==0) {
                            adviceText.text = "오늘은 목표치를 초과했어요 ! 경고 !!"
                        }else if(isDailyAchieve==-1) {
                            adviceText.text = "목표치나 일정을 입력하지 않았어요"
                        }else {
                            adviceText.text = "오늘은 목표치를 지켰어요 !! 칭찬해요 !"
                        }
                    }


                    1->{
                        adviceText.text = "내가 가장 많이 마신 술은 ${flagName} 입니다 !"
                    }
                    2-> {
                        val range = (0..5)
                        val randNum = range.random()
                        adviceText.text = advicearr[randNum]

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        return root
    }

    //오늘 날짜 기준으로 한달 간 목표 받아오는 함수
    @RequiresApi(Build.VERSION_CODES.O)
    fun getGoal(cmonth: Int): ArrayList<Int> {
        var dayGoal = ArrayList<Int>()
        lateinit var date: LocalDate
        var days = 0
        val now = LocalDate.now().toString()
        var parse = now.split("-")
        when (cmonth) {
            1 -> {
                date = LocalDate.of(parse[0].toInt(), 1, 1)
                days = 31
            }
            2 -> {
                date = LocalDate.of(parse[0].toInt(), 2, 1)
                days = 30
            }
            3 -> {
                date = LocalDate.of(parse[0].toInt(), 3, 1)
                days = 31
            }
            4 -> {
                date = LocalDate.of(parse[0].toInt(), 4, 1)
                days = 30
            }
            5 -> {
                date = LocalDate.of(parse[0].toInt(), 5, 1)
                days = 31
            }
            6 -> {
                date = LocalDate.of(parse[0].toInt(), 6, 1)
                days = 30
            }
            7 -> {
                date = LocalDate.of(parse[0].toInt(), 7, 1)
                days = 31
            }
            8 -> {
                date = LocalDate.of(parse[0].toInt(), 8, 1)
                days = 31
            }
            9 -> {
                date = LocalDate.of(parse[0].toInt(), 9, 1)
                days = 30
            }
            10 -> {
                date = LocalDate.of(parse[0].toInt(), 10, 1)
                days = 31
            }
            11 -> {
                date = LocalDate.of(parse[0].toInt(), 11, 1)
                days = 30
            }
            12 -> {
                date = LocalDate.of(parse[0].toInt(), 12, 1)
                days = 31
            }
        }


        for (i in 0..days - 1) {
            var changeDate = date.plusDays(i.toLong()).toString()
            dayGoal.add(DBHelper.findDailyGoal(changeDate).toInt()) // 날마다 종류별 1개씩 마신 양 가져오기
            //해야 할 작업 작성부분

        }

        return dayGoal

    }


}
