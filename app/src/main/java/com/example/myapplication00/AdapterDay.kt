package com.example.myapplication00

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication00.databinding.ListItemDayBinding
import java.text.SimpleDateFormat
import java.util.*

class AdapterDay(val context: Context, val tempMonth:Int, val dayList: MutableList<Date>, listener : OnItemClick): RecyclerView.Adapter<AdapterDay.DayView>() {

    var mydb: DBHelper = DBHelper(context)
    val ROW = 6
    private val mCallback = listener

    inner class DayView(val layout: ListItemDayBinding) : RecyclerView.ViewHolder(layout.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false)
        return DayView(ListItemDayBinding.bind(view))//DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        var date = dateFormatter.format(dayList[position])

        holder.layout.itemDayLayout.setOnClickListener {

            mCallback.setDate(date)

        }
        holder.layout.itemDayText.text = dayList[position].date.toString()

        if(position % 7 == 6){
            holder.layout.itemDayText.setTextColor(Color.BLUE)
        }else if(position % 7 == 0){
            holder.layout.itemDayText.setTextColor(Color.RED)
        }

        if(tempMonth != dayList[position].month) {
            holder.layout.itemDayText.alpha = 0.4f
        }

        //도장
        if (mydb.isDailyGoalAchieved(date) == 1){
            holder.layout.itemDayStamp.setImageResource(R.drawable.stamp_img_good)
        } else if(mydb.isDailyGoalAchieved(date) == 0) {
            holder.layout.itemDayStamp.setImageResource(R.drawable.stamp_img_bad)
        } else {
            holder.layout.itemDayStamp.setImageResource(R.drawable.stamp_none)
        }
        /*holder.layout.itemDayStamp.setImageResource(when(position) {
            //나중에 db 연동시 목표 달성 여부 따라 도장 good/bad/없음 변경
            //스크롤로 월 바뀔 때마다 해당 번호들 갱신해야 함
            3, 6, 7, 10, 19, 24 -> R.drawable.stamp_img_bad
            8, 9, 15, 16, 17, 32 -> R.drawable.stamp_img_good
            else -> R.drawable.stamp_none
        })*/
    }

    override fun getItemCount(): Int {
        return ROW * 7
    }
}