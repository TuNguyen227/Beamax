package com.androidcourse.g3.beamax.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.g3.beamax.DATA.DATE
import com.androidcourse.g3.beamax.databinding.DateItemBinding
import com.androidcourse.g3.beamax.interfaces.OnDateItemClick

class DateAdapter(listener: OnDateItemClick): ListAdapter<DATE,DateAdapter.DateHolder>(DateDiff()){
    private var onDateItemClickListener: OnDateItemClick

    init {
        onDateItemClickListener=listener
    }

    class DateDiff: DiffUtil.ItemCallback<DATE>(){
        override fun areItemsTheSame(oldItem: DATE, newItem: DATE): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: DATE, newItem: DATE): Boolean {
            return oldItem.date==newItem.date
        }
    }

    class DateHolder(var binding: DateItemBinding): RecyclerView.ViewHolder(binding.root){
        companion object
        {
            fun from(parent: ViewGroup) : DateHolder {
                val layout= LayoutInflater.from(parent.context)
                val binding = DateItemBinding.inflate(layout,parent,false)

                return DateHolder(binding)
            }
        }
        fun binding(item : DATE, listener: OnDateItemClick)
        {
            binding.dateNumTxt.text=item.date
            binding.dateTxt.text=item.dayOfWeek
            binding.monthTxt.text=item.month

            itemView.setOnClickListener(object : View.OnClickListener{
                @SuppressLint("ResourceAsColor")
                override fun onClick(v: View?) {
                    listener.onItemClick(item,adapterPosition)
                }
            })

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {

        return  DateHolder.from(parent)
    }





    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        holder.binding(getItem(position), onDateItemClickListener)
    }
}
