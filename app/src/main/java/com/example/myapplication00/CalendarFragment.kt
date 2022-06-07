package com.example.myapplication00

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.myapplication00.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {

    lateinit var binding : FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container,false)

        val view = inflater.inflate(R.layout.fragment_calendar, null)
        val regbtn = binding.regbtn
        regbtn.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, registerActivity::class.java)
                startActivity(intent)
            }
        })

        val monthListManager = LinearLayoutManager(requireActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false)
        val monthListAdapter = AdapterMonth(requireActivity().getApplicationContext())

        binding.calendarCustom.apply {
            layoutManager = monthListManager
            adapter = monthListAdapter
            scrollToPosition(Int.MAX_VALUE/2)
        }
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calendarCustom)

        return binding.root
    }

}