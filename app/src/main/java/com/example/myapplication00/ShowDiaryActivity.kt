package com.example.myapplication00

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        Log.d("DiaryData", diaryData.toString())

        binding.sojuCnt.text = convertText("soju", diaryData.soju)
        binding.beerCnt.text = convertText("beer", diaryData.beer)
        binding.makeolliCnt.text = convertText("makeolli", diaryData.makeolli)
        binding.wineCnt.text = convertText("wine", diaryData.wine)

        binding.diaryTextView.text = diaryData.diary
        binding.selfExaminationTextView.text = diaryData.self_examination
        binding.tipTextView.text = diaryData.tip
    }

    private fun convertText(alcoholType: String, count: Int) : String {

        var measure: Int = 1

        when(alcoholType) {
            "soju" -> measure = 7
            "beer" -> measure = 3
            "makeolli" -> measure = 4
            "wine" -> measure = 8
        }

        val bottle = count / measure
        val glass = count % measure

        if(bottle == 0) {
            return glass.toString() + "잔"
        } else if(glass == 0) {
            return bottle.toString() + "병"
        }

        return bottle.toString() + "병 " + glass.toString() + "잔"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== RESULT_OK) {
            Log.d("INIT", "INIT!!!")
            init()
        }
    }
}