package com.example.myapplication00

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class StatFragmentType : Fragment() {

    lateinit var dbHelper: DBHelper
    lateinit var activity : Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_stat_type, container, false)
        dbHelper = DBHelper(activity)

        val pieChart: PieChart = root.findViewById(R.id.pieChart)
        val array = dbHelper.getAllAlcohol()
        val total = array[0]+array[1]+array[2]+array[3]

        val Soper = (array[0].toDouble()/total.toDouble()*100.0)
        val Macper = (array[1].toDouble()/total.toDouble()*100.0)
        val Makper = (array[2].toDouble()/total.toDouble()*100.0)
        val Wineper = (array[3].toDouble()/total.toDouble()*100.0)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(Soper.toFloat(), "소주"))
        entries.add(PieEntry(Macper.toFloat(), "맥주"))
        entries.add(PieEntry(Makper.toFloat(), "막걸리"))
        entries.add(PieEntry(Wineper.toFloat(), "와인"))

        Log.d("TEST",array[0].toString())
        Log.d("TEST",array[1].toString())

        val colorItems = ArrayList<Int>()
        for(c in ColorTemplate.VORDIPLOM_COLORS) colorItems.add(c)
        for(c in ColorTemplate.JOYFUL_COLORS) colorItems.add(c)
        for(c in ColorTemplate.COLORFUL_COLORS) colorItems.add(c)
        for(c in ColorTemplate.LIBERTY_COLORS) colorItems.add(c)
        for(c in ColorTemplate.PASTEL_COLORS) colorItems.add(c)
        colorItems.add(ColorTemplate.getHoloBlue())

        val dataSet: PieDataSet = PieDataSet(entries, "주종")
        dataSet.apply {
            colors = colorItems
            valueTextColor = Color.BLACK
            valueTextSize = 14f
        }


        val pieData: PieData = PieData(dataSet)
        pieChart.apply{
            data = pieData
            description.isEnabled = false
            isRotationEnabled = false
            centerText = "내가 어떤 술을 \n 제일 많이 먹었지?"
            setEntryLabelColor(Color.BLACK)
            animateY(2000)
            animate()


        }

        return  root
    }

}