package com.example.myapplication00

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication00.databinding.FragmentCalendarBinding
import com.example.myapplication00.databinding.ListItemDayBinding
import com.example.myapplication00.databinding.ListItemMonthBinding
import java.util.*

class AdapterDay(val context: Context, val tempMonth:Int, val dayList: MutableList<Date>): RecyclerView.Adapter<AdapterDay.DayView>() {
    val ROW = 6

    //inner class DayView(val layout: View): RecyclerView.ViewHolder(layout)

    inner class DayView(val layout: ListItemDayBinding) : RecyclerView.ViewHolder(layout.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false)
        return DayView(ListItemDayBinding.bind(view))//DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {
        holder.layout.itemDayLayout.setOnClickListener {
            Toast.makeText(context, "${dayList[position]}", Toast.LENGTH_SHORT).show()
        }
        holder.layout.itemDayText.text = dayList[position].date.toString()

        holder.layout.itemDayText.setTextColor(when(position % 7) {
            0 -> Color.RED
            6 -> Color.BLUE
            else -> Color.BLACK
        })

        if(tempMonth != dayList[position].month) {
            holder.layout.itemDayText.alpha = 0.4f
        }

        holder.layout.itemDayStamp.setImageResource(when(position) {
            //나중에 db 연동시 목표 달성 여부 따라 도장 good/bad/없음 변경
            //스크롤로 월 바뀔 때마다 해당 번호들 갱신해야 함
            3, 6, 7, 10, 19, 24 -> R.drawable.stamp_img_bad
            8, 9, 15, 16, 17, 32 -> R.drawable.stamp_img_good
            else -> R.drawable.stamp_none
        })
    }

    override fun getItemCount(): Int {
        return ROW * 7
    }
}