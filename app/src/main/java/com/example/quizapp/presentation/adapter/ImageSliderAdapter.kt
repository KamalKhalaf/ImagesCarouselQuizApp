package com.example.quizapp.presentation.adapter

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.quizapp.data.ImageItem
import com.example.quizapp.presentation.views.ImageFragment
import com.example.quizapp.presentation.base.MainActivity

/**
 * @Created by: Kamal.Farghali
 * @Date: 02/11/2023 : 7:10 PM
 */

class ImageSliderAdapter(private var context: Activity, private var imagesData: List<ImageItem>) : FragmentStateAdapter(context as MainActivity) {

    override fun getItemCount(): Int = imagesData.size
    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putParcelable("ARG_IMAGE_OBJECT" , imagesData[position])
        return ImageFragment.newInstance(position, bundle)
    }
}