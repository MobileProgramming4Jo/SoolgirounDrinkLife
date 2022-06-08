package com.example.myapplication00

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication00.databinding.RowSettingBinding

class SettingsAdapter(val items: ArrayList<String>): RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    public interface ItemClickListener {
        public fun onClickItem(index: Int)
    }

    var itemClickListener: ItemClickListener? = null

    inner class ViewHolder(val binding: RowSettingBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.settingRowTextView.setOnClickListener {
                itemClickListener?.onClickItem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.settingRowTextView.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

