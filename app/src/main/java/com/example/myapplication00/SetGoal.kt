package com.example.myapplication00
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import com.example.myapplication00.databinding.ActivitySetGoalBinding
import kotlinx.android.synthetic.main.activity_set_goal.*
import java.time.LocalDate

class SetGoal : AppCompatActivity() {
    lateinit var binding : ActivitySetGoalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout(){

        val text = binding.ex1.text.toString()
        val color = Color.rgb(127,145,239)
        val span = SpannableStringBuilder(text)
        span.apply {
            setSpan(ForegroundColorSpan(color), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(Color.BLACK), 4, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.ex1.text = span
        val text2 = binding.ex2.text.toString()
        val color2 = Color.rgb(127, 209, 175)
        val span2 = SpannableStringBuilder(text2)
        span2.apply {
            setSpan(ForegroundColorSpan(color2), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(Color.BLACK), 4, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.ex2.text = span2

        binding.apply {
            setGoal.setOnClickListener {
                if(week_goal.text != null && day_goal.text != null){
                    val myDBHelper = DBHelper(this@SetGoal)
                    val daily = day_goal.text.toString().toInt()
                    val weekly = week_goal.text.toString().toInt()
                    val now = LocalDate.now()
                    myDBHelper.updateGoal(now.toString(), daily, weekly)
                    Toast.makeText(this@SetGoal, "성공적으로 목표를 변경했습니다.", Toast.LENGTH_SHORT).show()
                    /*val intent = Intent(this@SetGoal, MainActivity::class.java)
                    startActivity(intent)*/
                    finish()
                }else{
                    Toast.makeText(this@SetGoal, "목표를 제대로 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}