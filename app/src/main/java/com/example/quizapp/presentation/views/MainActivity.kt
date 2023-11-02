package com.example.quizapp.presentation.views

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.base.BaseActivity
import com.example.quizapp.data.ImageItem
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.presentation.adapter.ImageSliderAdapter

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var imagesData : ArrayList<ImageItem>
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun initViews() {


        imageSliderAdapter = ImageSliderAdapter(this, imagesData)

        binding.vpViewPager.apply {
            adapter = imageSliderAdapter
            registerOnPageChangeCallback(onPageChangeCallback)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = cards.size
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
        }
    }
}