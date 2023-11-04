package com.example.quizapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.data.ImageContent
import com.example.quizapp.databinding.ImageContentItemBinding

/**
 * @Created by: Kamal.Farghali
 * @Date: 04/11/2023 : 3:12 AM
 */

class ImageContentAdapter (private var imagesContent: List<ImageContent>) : RecyclerView.Adapter<ImageContentAdapter.ViewHolder>() {
    override fun getItemCount(): Int = imagesContent.size
    override fun onBindViewHolder(holder: ImageContentAdapter.ViewHolder, position: Int) = holder.bind(imagesContent[position])
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageContentItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, parent)
    }

    fun updateList(list : List<ImageContent>){
        imagesContent = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ImageContentItemBinding, private var parent: View) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cardsCircle: ImageContent) {
            binding.tvImageContent.text = cardsCircle.name
        }
    }
}