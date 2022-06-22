package com.androidcourse.g3.beamax.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.g3.beamax.DATA.Categories
import com.androidcourse.g3.beamax.DATA.Dish
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.databinding.CategoryItemViewBinding
import com.androidcourse.g3.beamax.databinding.DishItemBinding
import com.androidcourse.g3.beamax.interfaces.OnCategoryClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import okhttp3.internal.immutableListOf

class DishAdapter(listener: OnDishClickListener): ListAdapter<Dish, DishAdapter.DishHolder>(DishDiffcallback()){
    private var onDishClickListener: OnDishClickListener
    init {
        onDishClickListener=listener
    }
    class DishDiffcallback : DiffUtil.ItemCallback<Dish>() {
        override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean {

            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean {

            return oldItem.name==newItem.name
        }
    }

    class DishHolder(var binding: DishItemBinding): RecyclerView.ViewHolder(binding.root){
        companion object
        {
            fun from(parent: ViewGroup) : DishHolder {
                val layout= LayoutInflater.from(parent.context)
                val binding = DishItemBinding.inflate(layout,parent,false)

                return DishHolder(binding)
            }
        }
        fun binding(item : Dish, listener: OnDishClickListener)
        {
           binding.dishName.text=item.name
            binding.dishPrice.text=item.price.toString()
            val rm_btn=itemView.findViewById<ImageButton>(R.id.btn_remove)
            val add_btn=itemView.findViewById<ImageButton>(R.id.btn_add)
            val number=itemView.findViewById<TextView>(R.id.number)
            add_btn.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    listener.onItemClick(item,add_btn,number)
                }
            })

            rm_btn.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    listener.onItemClick(item,rm_btn,number)
                }
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishHolder {

        return  DishHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DishHolder, position: Int) {

        val dish=getItem(position)
        holder.binding(dish,onDishClickListener)
    }
}


interface OnDishClickListener{
    fun onItemClick(dish: Dish,btn:ImageButton,number:TextView)
}