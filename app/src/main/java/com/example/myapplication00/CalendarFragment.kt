package com.example.myapplication00

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.myapplication00.databinding.FragmentCalendarBinding
import java.time.LocalDate
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import java.time.format.DateTimeFormatter


class CalendarFragment : Fragment(), OnItemClick {
    private lateinit var binding : FragmentCalendarBinding
    private val myViewModel: MyViewModel by activityViewModels()
    lateinit var mydb: DBHelper
    lateinit var activity : Activity
    lateinit var monthListAdapter : AdapterMonth

    var dateStr = ""

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container,false)
        mydb = DBHelper(activity)

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                setViewModelData()
            }
        }

        val view = inflater.inflate(R.layout.fragment_calendar, null)
        val regbtn = binding.regbtn
        regbtn.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, registerActivity::class.java)
                startActivity(intent)
            }
        })

        val monthListManager = LinearLayoutManager(requireActivity().applicationContext, LinearLayoutManager.HORIZONTAL, false)
        monthListAdapter = AdapterMonth(requireActivity().applicationContext, this)

        binding.calendarCustom.apply {
            layoutManager = monthListManager
            adapter = monthListAdapter
            scrollToPosition(Int.MAX_VALUE/2)
        }
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calendarCustom)

        setViewModelData()

        binding.diaryBoxNone.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, DiaryActivity::class.java)
                intent.putExtra("date", dateStr)
                intent.putExtra("diaryData", DiaryData(
                    false, 0, 0, 0, 0, "", "", "")
                )
                activityResultLauncher.launch(intent)
            }
        })
        binding.diaryBoxBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, ShowDiaryActivity::class.java)
                intent.putExtra("date", binding.diaryDate.text)
                startActivity(intent)
            }
        })

        return binding.root
    }

    override fun setDate(date: String) {
        myViewModel.setLiveData(date)
        dateStr = date
        var dateParse = date.split("-")
        binding.dateText.text = "${dateParse[0]}년 ${dateParse[1]}월 ${dateParse[2]}일"

        if(date > LocalDate.now().toString()) {
            binding.diaryBox.isVisible = false
            binding.diaryBoxNone.isVisible = false
            binding.diaryTitleText.isVisible = false
        }

    }

    override fun onStart() {
        super.onStart()
        val date: String = LocalDate.now().toString()
        val daily_goal = mydb.findDailyGoal(date)
        val weekly_goal = mydb.findWeeklyGoal(date)
        binding.dailygoaltext.text = "소주 "+daily_goal+"병 이하"
        binding.weeklygoaltext.text = "소주 "+weekly_goal+"병 이하"

    }

    private fun setViewModelData() {
        myViewModel.selectedDate.observe(viewLifecycleOwner, Observer {
            //schedulebox
            if(mydb.getScheduleTitle(it) != ""){ //데이터 존재
                binding.scheduleBox.isVisible = true
                binding.scheduleBoxNone.isVisible = false
                binding.scheduleBoxTitle.text = mydb.getScheduleTitle(it)
                binding.scheduleBoxLocation.text = mydb.getScheduleLocation(it)
                binding.scheduleBoxTime.text = mydb.getScheduleTime(it)
            } else { //존재하지 않음
                binding.scheduleBox.isVisible = false
                binding.scheduleBoxNone.isVisible = true
            }

            //diarybox

            var alcoholString = mydb.getAlcoholString(it)
            if(alcoholString != "" && alcoholString != "null"){ //데이터 존재
                binding.diaryBox.isVisible = true
                binding.diaryBoxNone.isVisible = false
                binding.diaryDate.text = it
                binding.diarytypetext.text = alcoholString
            } else { //존재하지 않음
                binding.diaryBox.isVisible = false
                binding.diaryBoxNone.isVisible = true
            }
        })
    }
}

