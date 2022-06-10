package com.example.myapplication00

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import com.example.myapplication00.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryBinding
    lateinit var diaryData: DiaryData
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        dbHelper = DBHelper(this)
        diaryData = intent.getSerializableExtra("diaryData") as DiaryData

        binding.sojuPMBar.findViewById<TextView>(R.id.countText).text = diaryData.soju.toString()
        binding.sojuPMBar.countNum = diaryData.soju

        binding.beerPMBar.findViewById<TextView>(R.id.countText).text = diaryData.beer.toString()
        binding.beerPMBar.countNum = diaryData.beer

        binding.makeolliPMBar.findViewById<TextView>(R.id.countText).text = diaryData.makeolli.toString()
        binding.makeolliPMBar.countNum = diaryData.makeolli

        binding.winePMBar.findViewById<TextView>(R.id.countText).text = diaryData.wine.toString()
        binding.winePMBar.countNum = diaryData.wine

        binding.diaryEditText.setText(diaryData.diary)
        binding.selfExaminationEditText.setText(diaryData.self_examination)
        binding.tipEditText.setText(diaryData.tip)

        val date = intent.getStringExtra("date")

        binding.saveButton.setOnClickListener {
            val isExist = diaryData.isExist


            var soju = binding.sojuPMBar.findViewById<TextView>(R.id.countText).text.toString().toInt()
            var beer = binding.beerPMBar.findViewById<TextView>(R.id.countText).text.toString().toInt()
            var makeolli = binding.makeolliPMBar.findViewById<TextView>(R.id.countText).text.toString().toInt()
            var wine = binding.winePMBar.findViewById<TextView>(R.id.countText).text.toString().toInt()

            if(binding.sojuSwitchButton.isBottle) {
                soju *= 7
            }

            if(binding.beerSwitchButton.isBottle) {
                beer *= 3
            }

            if(binding.makeolliSwitchButton.isBottle) {
                makeolli *= 4
            }

            if(binding.wineSwitchButton.isBottle) {
                wine *= 8
            }

            diaryData = DiaryData(
                isExist = true,
                soju = soju,
                beer = beer,
                makeolli = makeolli,
                wine = wine,
                diary = binding.diaryEditText.text.toString(),
                self_examination = binding.selfExaminationEditText.text.toString(),
                tip = binding.tipEditText.text.toString()
            )

            var isSucceed = false

            isSucceed = dbHelper.updateDiary(date!!, diaryData)
            Log.d("DATE", date.toString())
            Log.d("DiaryData", diaryData.toString())
            Log.d("DiaryDataisSucceed", isSucceed.toString())
            if (isSucceed) {
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                val intent = Intent()
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }
        }

    }
}