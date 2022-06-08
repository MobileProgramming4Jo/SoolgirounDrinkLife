package com.example.myapplication00

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication00.databinding.ActivityShowDiaryBinding
import com.example.projectapp.DBHelper

class ShowDiaryActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowDiaryBinding
    lateinit var diaryData: DiaryData
    lateinit var dBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        dBHelper = DBHelper(this)

        val date = intent.getStringExtra("date")
        diaryData = dBHelper.getDiary(date!!)

        binding.editButton.setOnClickListener {
            val intent = Intent(this, DiaryActivity::class.java)
            intent.putExtra("diaryData", diaryData)
            intent.putExtra("date", date)
            startActivity(intent)
        }

        binding.sojuCnt.text = convertText("soju", diaryData.soju)
        binding.beerCnt.text = convertText("beer", diaryData.beer)
        binding.makeolliCnt.text = convertText("makeolli", diaryData.makeolli)
        binding.wineCnt.text = convertText("wine", diaryData.wine)
    }

    private fun convertText(alcoholType: String, count: Int) : String {
        if (alcoholType == "soju") {
            val bottle = count / 7
            val glass = count % 7
            return bottle.toString() + "병 " + glass.toString() + "잔"
        }

        return ""
    }
}