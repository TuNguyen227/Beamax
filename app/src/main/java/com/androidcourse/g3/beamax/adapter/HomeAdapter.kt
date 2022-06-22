package com.androidcourse.g3.beamax.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.databinding.ItemBinding
import com.androidcourse.g3.beamax.interfaces.OnItemClickListener
import com.androidcourse.group3.beamax.DATA.Restaurants
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class HomeAdapter(listener: OnItemClickListener) : ListAdapter<Restaurants,HomeAdapter.HomeviewHolder>(UserDiffcallback()){
    private  var onItemClickListener :OnItemClickListener
    init {
        onItemClickListener=listener
    }
    class UserDiffcallback : DiffUtil.ItemCallback<Restaurants>() {
        override fun areItemsTheSame(oldItem: Restaurants, newItem: Restaurants): Boolean {

            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Restaurants, newItem: Restaurants): Boolean {

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
        fun binding(item : Restaurants,listener: OnItemClickListener)
        {
            val requestOptions=RequestOptions
                .centerCropTransform()
                .override(200,200)
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.ic_baseline_mail_24)
            binding.itemName.text=item.name
            binding.itemDesc.text=item.address
            binding.progBar.visibility=View.VISIBLE
            Glide.with(itemView.context).load(item.icnURL).listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progBar.visibility=View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progBar.visibility=View.GONE
                    return false
                }
            }).apply(requestOptions).into(binding.itemImg)
            itemView.setOnClickListener(object:View.OnClickListener{
                override fun onClick(v: View?) {
                    listener.onItemClick(item,adapterPosition)
                }

            })

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeviewHolder {

        return  HomeviewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeviewHolder, position: Int) {

        val user=getItem(position)
        holder.binding(user, onItemClickListener )


    }
}