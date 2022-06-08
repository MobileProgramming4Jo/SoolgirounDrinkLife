package com.example.myapplication00

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication00.databinding.ActivityShowDiaryBinding

class ShowDiaryActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editButton.setOnClickListener {
            val intent = Intent(this, DiaryActivity::class.java)
            startActivity(intent)
        }
    }
}