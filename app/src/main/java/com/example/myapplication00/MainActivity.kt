package com.example.myapplication00

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication00.databinding.ActivityMainBinding
import com.example.myapplication00.databinding.ActivityRegisterBinding
import com.example.projectapp.DBHelper
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        initAlarm()
    }

    private fun initlayout() {
        binding.viewpager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewpager){
            tab, position ->
                tab. text = textarr[position]
                tab.setIcon(icontarr[position])
        }.attach()

    }
    private fun initAlarm(){

        val receiverIntent = Intent(this@MainActivity, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this@MainActivity,
            7,
            receiverIntent,
            PendingIntent.FLAG_NO_CREATE
        )
        if(pendingIntent != null){
            pendingIntent.cancel()
        }


        val receiverIntent2 = Intent(this@MainActivity, DayReceiver::class.java)
        val pendingIntent2 = PendingIntent.getBroadcast(
            this@MainActivity,
            1,
            receiverIntent2,
            PendingIntent.FLAG_NO_CREATE
        )
        if(pendingIntent2 == null){
            setDayAlarm()
        }
    }

    override fun onStop() {
        super.onStop()
        setWeekAlarm()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setDayAlarm() {
        val alarmManagerDay = getSystemService(ALARM_SERVICE) as AlarmManager
        val receiverIntent = Intent(this@MainActivity, DayReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this@MainActivity,
            1,
            receiverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val calendar = java.util.Calendar.getInstance().apply {

            timeInMillis = System.currentTimeMillis()
            set(java.util.Calendar.HOUR_OF_DAY, 8)
        }


        if (Build.VERSION.SDK_INT >= 23) {
            alarmManagerDay.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }else {
            if(Build.VERSION.SDK_INT >= 19){
                alarmManagerDay.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }else{
                alarmManagerDay.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
        }

        Log.v("alarm", "dayReceiver")
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
        myDBHelper.checkData(date)
    }

    private fun setWeekAlarm() {

        val routine = CoroutineScope(Dispatchers.IO)
        routine.apply {
            val alarmManagerWeek = getSystemService(ALARM_SERVICE) as AlarmManager
            val receiverIntent = Intent(this@MainActivity, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this@MainActivity,
                7,
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val calendar = java.util.Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 8)
                add(Calendar.DATE, 14)
            }
            if (Build.VERSION.SDK_INT >= 23) {
                alarmManagerWeek.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }else {
                if(Build.VERSION.SDK_INT >= 19){
                    alarmManagerWeek.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                }else{
                    alarmManagerWeek.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                }
            }
            Log.v("alarm","set")
        }
    }
}