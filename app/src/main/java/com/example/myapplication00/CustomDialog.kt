package com.example.myapplication00


import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.projectapp.DBHelper
import java.time.LocalDate

class CustomDialog(val context : Context) {

    private val dialog = Dialog(context)
    val myDBHelper = DBHelper(context)
    fun myDig(){
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDlg(){
        dialog.setContentView(R.layout.object_dialog)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)


        val now = LocalDate.now()
        val day_object = myDBHelper.findDailyGoal(now.toString())
        val week_object = myDBHelper.findWeeklyGoal(now.toString())
        val daily = dialog.findViewById<EditText>(R.id.day_object)
        val weekly = dialog.findViewById<EditText>(R.id.week_object)

        daily.setText(day_object)
        weekly.setText(week_object)

        val pButton = dialog.findViewById<Button>(R.id.button)
        val nButton = dialog.findViewById<Button>(R.id.button2)

        pButton.setOnClickListener {
            val daily_goal = daily.text.toString().toInt()
            val weekly_goal = weekly.text.toString().toInt()
            myDBHelper.updateGoal(now.toString(), daily_goal, weekly_goal)
            Toast.makeText(context, "성공적으로 변경하였습니다.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        nButton.setOnClickListener {
            dialog.dismiss()
        }

    }


}