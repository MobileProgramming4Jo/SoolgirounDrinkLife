package com.example.myapplication00

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication00.databinding.CustomPlusMinusBarBinding
import com.example.myapplication00.databinding.CustomSwitchButtonBinding

open class CustomSwitchButton @JvmOverloads constructor(context: Context, var attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private val binding: CustomSwitchButtonBinding = CustomSwitchButtonBinding.inflate(
        LayoutInflater.from(context), this, true)

    // false : 잔, true: 병
    var isBottle: Boolean = false

    init {
        binding.leftSwitchButton.setOnClickListener {
            binding.leftSwitchButton.setBackgroundResource(R.drawable.left_border_radius_rectangle)
            binding.rightSwitchButton.setBackgroundResource(R.drawable.right_border_radius_gray_rectangle)
            isBottle = false
        }

        binding.rightSwitchButton.setOnClickListener {
            binding.leftSwitchButton.setBackgroundResource(R.drawable.left_border_radius_gray_rectangle)
            binding.rightSwitchButton.setBackgroundResource(R.drawable.right_border_radius_rectangle)
            isBottle = true
        }
    }
}