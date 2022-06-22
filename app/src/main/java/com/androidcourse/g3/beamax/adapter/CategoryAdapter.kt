package com.androidcourse.g3.beamax.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.g3.beamax.DATA.Categories
import com.androidcourse.g3.beamax.R
import com.androidcourse.g3.beamax.databinding.CategoryItemViewBinding
import com.androidcourse.g3.beamax.databinding.ItemBinding
import com.androidcourse.g3.beamax.interfaces.OnCategoryClickListener
import com.androidcourse.group3.beamax.DATA.Restaurants
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CategoryAdapter(listener:OnCategoryClickListener): ListAdapter<Categories, CategoryAdapter.CategoryHolder>(CategoryDiffcallback()){
    private var onCategoryClickListener:OnCategoryClickListener
    init {
        onCategoryClickListener=listener
    }
    class CategoryDiffcallback : DiffUtil.ItemCallback<Categories>() {
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {

            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {

            return oldItem.name==newItem.name
        }
    }

    class CategoryHolder(var binding: CategoryItemViewBinding): RecyclerView.ViewHolder(binding.root){
        companion object
        {
            fun from(parent: ViewGroup) : CategoryHolder {
                val layout= LayoutInflater.from(parent.context)
                val binding = CategoryItemViewBinding.inflate(layout,parent,false)

                return CategoryHolder(binding)
            }
        }
        fun binding(item : Categories,listener: OnCategoryClickListener)
        {
            val requestOptions= RequestOptions
                .centerCropTransform()
                .override(200,200)
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.ic_baseline_mail_24)
            binding.nameCategory.text=item.name
            Glide.with(itemView.context).load(item.image).apply(requestOptions).into(binding.imageCategory)
            itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    listener.onCategoryClick(item)

                }
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {

        return  CategoryHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {

        val user=getItem(position)
        holder.binding(user,onCategoryClickListener)
    }
}