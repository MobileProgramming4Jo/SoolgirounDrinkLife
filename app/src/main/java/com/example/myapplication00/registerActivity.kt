package com.example.myapplication00

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
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
        binding.regbtn.setOnClickListener {
            val regIntent = Intent(this, MainActivity::class.java)
            intent.putExtra("title",binding.titletext.toString())
            intent.putExtra("location",binding.locationtext.toString())
            intent.putExtra("isallday",isallday)
            intent.putExtra("start_time",start_time)
            intent.putExtra("end_time",end_time)
            intent.putExtra("alarm",alarm)
            intent.putExtra("memo",binding.memotext.toString())
            startActivity(regIntent)
        }

        binding.isalldaytext.setOnClickListener {
            if (isallday){
                binding.isalldaytext.setHintTextColor(resources.getColor(R.color.black))
                isallday = false
            } else {
                binding.isalldaytext.setHintTextColor(resources.getColor(R.color.green))
                isallday = true
            }
        }

        val cal = Calendar.getInstance()
        binding.starttimetext.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                start_time = "${hourOfDay}${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }
        binding.endtimetext.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                end_time = "${hourOfDay}${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }
        binding.alarmtext.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                alarm = "${hourOfDay}${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

    }
}