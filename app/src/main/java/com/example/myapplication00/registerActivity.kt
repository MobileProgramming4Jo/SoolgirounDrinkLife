package com.example.myapplication00

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.myapplication00.databinding.ActivityRegisterBinding
import java.text.SimpleDateFormat
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
            if (binding.titletext.text.toString() == ""){
                Toast.makeText(this, "제목을 입력해야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val regIntent = Intent(this, MainActivity::class.java)
            intent.putExtra("title",binding.titletext.text.toString())
            intent.putExtra("location",binding.locationtext.text.toString())
            intent.putExtra("isallday",isallday)
            if(isallday){
                start_time = start_time_date
                end_time = end_time_date
            } else {
                start_time = start_time_date + " " + start_time_hour
                end_time = end_time_date + " " + end_time_hour
            }
            intent.putExtra("start_time",start_time)
            intent.putExtra("end_time",end_time)
            intent.putExtra("alarm",alarm)
            intent.putExtra("memo",binding.memotext.text.toString())

            //테스트
            //Toast.makeText(this, isallday.toString(), Toast.LENGTH_SHORT).show()
            mydb.insertDairy(start_time_date, binding.titletext.text.toString(), binding.locationtext.text.toString(),
                isallday, start_time, end_time, alarm, binding.memotext.text.toString())
            startActivity(regIntent)
        }

        binding.isalldaytext.setOnClickListener {
            if (isallday){
                binding.isalldaytext.setHintTextColor(resources.getColor(R.color.black))
                isallday = false
                binding.starttimeHour.isGone = false
                binding.endtimeHour.isGone = false
            } else {
                binding.isalldaytext.setHintTextColor(resources.getColor(R.color.green))
                isallday = true
                binding.starttimeHour.isGone = true
                binding.endtimeHour.isGone = true
            }
        }

        val cal = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val hourFormatter = SimpleDateFormat("kk:mm")
        binding.starttimeDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                start_time_date = dateFormatter.format(cal.time)
                //테스트용
                //Toast.makeText(this, cal.time.toString(), Toast.LENGTH_SHORT).show()
                //Toast.makeText(this, "${year}-${month}-${dayOfMonth}", Toast.LENGTH_SHORT).show()
                //Toast.makeText(this, start_time_date, Toast.LENGTH_SHORT).show()
                binding.starttimeDate.text = start_time_date
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.starttimeHour.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                start_time_hour = hourFormatter.format(cal.time)
                binding.starttimeHour.text = start_time_hour
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        binding.endtimeDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                //Toast.makeText(this, cal.time.toString(), Toast.LENGTH_SHORT).show()
                end_time_date = dateFormatter.format(cal.time)
                binding.endtimeDate.text = end_time_date
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.endtimeHour.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                end_time_hour = hourFormatter.format(cal.time)
                binding.endtimeHour.text = end_time_hour
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        binding.alarmHour.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                alarm = hourFormatter.format(cal.time)
                binding.alarmHour.text = alarm
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

    }
}