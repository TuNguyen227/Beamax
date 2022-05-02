package com.androidcourse.g3.beamax.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.databinding.ItemBinding
import com.androidcourse.group3.beamax.DATA.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.RequestOptions

class HomeAdapter() : ListAdapter<User,HomeAdapter.HomeviewHolder>(UserDiffcallback()){
    class UserDiffcallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {

            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {

            return oldItem.name==newItem.name
        }
    }

    class HomeviewHolder(var binding:ItemBinding): RecyclerView.ViewHolder(binding.root){
        companion object
        {
            fun from(parent:ViewGroup) : HomeviewHolder {
                val layout=LayoutInflater.from(parent.context)
                val binding =ItemBinding.inflate(layout,parent,false)

                return HomeviewHolder(binding)
            }
        }
        fun binding(item : User)
        {
            val requestOptions=RequestOptions
                .centerCropTransform()
                .override(200,200)
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.ic_baseline_mail_24)
            binding.itemName.text=item.name
            binding.itemDesc.text=item.age.toString()
            Glide.with(itemView.context).load(item.imageURL).apply(requestOptions).into(binding.itemImg)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeviewHolder {

        return  HomeviewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeviewHolder, position: Int) {

        val user=getItem(position)
        holder.binding(user)
    }
}