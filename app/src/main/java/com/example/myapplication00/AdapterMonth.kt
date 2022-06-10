package com.example.myapplication00

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication00.databinding.ListItemMonthBinding
import java.util.*

class AdapterMonth(val context: Context, listener : OnItemClick): RecyclerView.Adapter<AdapterMonth.MonthView>() {

    val center = Int.MAX_VALUE / 2
    private var calendar = Calendar.getInstance()

    //inner class MonthView(val layout: View): RecyclerView.ViewHolder(layout)
    inner class MonthView(val layout: ListItemMonthBinding) : RecyclerView.ViewHolder(layout.root)
    val listener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_month, parent, false)
        return MonthView(ListItemMonthBinding.bind(view))//MonthView(view)
    }

    override fun onBindViewHolder(holder: MonthView, position: Int) {
        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - center)
        holder.layout.itemMonthText.text = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"
        val tempMonth = calendar.get(Calendar.MONTH)

        var dayList: MutableList<Date> = MutableList(6 * 7) { Date() }
        for(i in 0..5) {
            for(k in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + k)
                dayList[i * 7 + k] = calendar.time
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        val dayListManager = GridLayoutManager(context, 7) //holder.layout.context
        val dayListAdapter = AdapterDay(context ,tempMonth, dayList, listener)

        holder.layout.itemMonthDayList.apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }
    }



    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}