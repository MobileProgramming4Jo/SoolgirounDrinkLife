package com.example.myapplication00

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.myapplication00.databinding.ActivityMainBinding
import com.example.myapplication00.databinding.ActivityRegisterBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*

class registerActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    var isallday: Boolean = false
    var start_time: String = ""
    var end_time: String = ""
    var alarm: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initlayout()
    }

    private fun initlayout() {
        var start_time_date: String = ""
        var start_time_hour: String = ""
        var end_time_date: String = ""
        var end_time_hour: String = ""

        binding.regbtn.setOnClickListener {
            val regIntent = Intent(this, MainActivity::class.java)
            intent.putExtra("title",binding.titletext.toString())
            intent.putExtra("location",binding.locationtext.toString())
            intent.putExtra("isallday",isallday)
            start_time = start_time_date + " " + start_time_hour
            intent.putExtra("start_time",start_time)
            end_time = end_time_date + " " + end_time_hour
            intent.putExtra("end_time",end_time)
            intent.putExtra("alarm",alarm)
            intent.putExtra("memo",binding.memotext.toString())
            startActivity(regIntent)
        }

        binding.isalldaytext.setOnClickListener {
            if (isallday){
                binding.isalldaytext.setHintTextColor(resources.getColor(R.color.black))
                isallday = false
                binding.starttimeHour.isGone = true
                binding.endtimeHour.isGone = true
            } else {
                binding.isalldaytext.setHintTextColor(resources.getColor(R.color.green))
                isallday = true
                binding.starttimeHour.isGone = false
                binding.endtimeHour.isGone = false
            }
        }

        val cal = Calendar.getInstance()
        binding.starttimeDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                start_time_date = "${year}.${month+1}.${dayOfMonth}"
                binding.starttimeDate.setText(start_time_date)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.starttimeHour.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                start_time_hour = "${hourOfDay}:${minute}"
                binding.starttimeHour.setText(start_time_hour)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        binding.endtimeDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                end_time_date = "${year}.${month+1}.${dayOfMonth}"
                /*if(end_time_date.compareTo(start_time_date) < 0 ||
                    ( end_time_date.compareTo(start_time_date) == 0 ) && (end_time_hour.compareTo(start_time_hour) < 0 ) ){
                    Toast.makeText(this, "종료 날짜는 시작 날짜 이후의 날짜여야 합니다.", Toast.LENGTH_SHORT).show()
                    return@OnDateSetListener
                }*/
                binding.endtimeDate.setText(end_time_date)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.endtimeHour.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                end_time_hour = "${hourOfDay}:${minute}"
                binding.endtimeHour.setText(end_time_hour)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        binding.alarmHour.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                alarm = "${hourOfDay}:${minute}"
                binding.alarmHour.setText(alarm)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

    }
}