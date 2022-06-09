package com.example.myapplication00

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.myapplication00.databinding.CustomPlusMinusBarBinding


open class CustomPlusMinusBar @JvmOverloads constructor(context: Context, var attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val binding: CustomPlusMinusBarBinding = CustomPlusMinusBarBinding.inflate(
        LayoutInflater.from(context), this, true)

    var countNum: Int = 0

    init {
        binding.minusButton.setOnClickListener {
            if(countNum > 0) {
                countNum--
                binding.countText.text = countNum.toString()
            }
        }

        binding.plusButton.setOnClickListener {
            if(countNum < 10) {
                countNum++
                binding.countText.text = countNum.toString()
            }
        }

    }
}