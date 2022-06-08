package com.example.myapplication00

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.myapplication00.databinding.ActivityRegisterBinding
import com.example.projectapp.DBHelper
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class registerActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    var isallday: Boolean = false
    var start_time: String = ""
    var end_time: String = ""
    var alarm: String = ""
    var mydb: DBHelper = DBHelper(this)

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
            mydb.insertDairy(binding.titletext.toString(), binding.locationtext.toString(),
                isallday, start_time, end_time, alarm, binding.memotext.toString())

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
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val hourFormatter = SimpleDateFormat("kk:mm")
        binding.starttimeDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.time = Date()
                start_time_date = dateFormatter.format(cal.getTime())
                //Toast.makeText(this, start_time_date, Toast.LENGTH_SHORT).show() //테스트용
                binding.starttimeDate.setText(start_time_date)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.starttimeHour.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.time = Date()
                start_time_hour = hourFormatter.format(cal.getTime())
                binding.starttimeHour.setText(start_time_hour)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        binding.endtimeDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.time = Date()
                end_time_date = dateFormatter.format(cal.getTime())
                binding.endtimeDate.setText(end_time_date)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.endtimeHour.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.time = Date()
                end_time_hour = hourFormatter.format(cal.getTime())
                binding.endtimeHour.setText(end_time_hour)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        binding.alarmHour.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.time = Date()
                alarm = hourFormatter.format(cal.getTime())
                binding.alarmHour.setText(alarm)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

    }
}