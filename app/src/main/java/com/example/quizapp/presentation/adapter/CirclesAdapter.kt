package com.example.quizapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.common.gone
import com.example.quizapp.common.visible
import com.example.quizapp.data.CircleActiveModel
import com.example.quizapp.databinding.CircleItemBinding

/**
 * @Created by: Kamal.Farghali
 * @Date: 02/11/2023 : 8:04 PM
 */

internal class CirclesAdapter(private var cardsCircleList: List<CircleActiveModel>) : RecyclerView.Adapter<CirclesAdapter.ViewHolder>() {
    override fun getItemCount(): Int = cardsCircleList.size
    override fun onBindViewHolder(holder: CirclesAdapter.ViewHolder, position: Int) = holder.bind(cardsCircleList[position])
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CircleItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, parent)
    }

    fun update(list: List<CircleActiveModel>) {
        cardsCircleList = list
        notifyDataSetChanged()
    }

    internal inner class ViewHolder(private var binding: CircleItemBinding, private var parent: View) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cardsCircle: CircleActiveModel) {
            if (cardsCircle.type == 1) binding.ivCircle.visible() else binding.ivCircle.gone()
        }
    }
}