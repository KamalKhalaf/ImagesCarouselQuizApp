package com.example.quizapp.presentation.views

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.quizapp.R
import com.example.quizapp.base.BaseFragment
import com.example.quizapp.data.CircleActiveModel
import com.example.quizapp.data.ImageContent
import com.example.quizapp.data.ImageItem
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.presentation.adapter.CirclesAdapter
import com.example.quizapp.presentation.adapter.ImageSliderAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var circlesAdapter: CirclesAdapter
    private lateinit var imagesData : List<ImageItem>
    private var circleList: ArrayList<CircleActiveModel> = ArrayList()

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateCircleMarker(position)
        }
    }
    override fun initViews() {

        imagesData = listOf(
            ImageItem(R.drawable.image1, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"), ImageContent("Item 5"))),
            ImageItem(R.drawable.image2, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"))),
            ImageItem(R.drawable.image3, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"))),
            ImageItem(R.drawable.image4, listOf(ImageContent("Item 1"), ImageContent("Item 2"))),
            ImageItem(R.drawable.image5, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"), ImageContent("Item 5"), ImageContent("Item 6"), ImageContent("Item 7"))),
            ImageItem(R.drawable.image6, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"), ImageContent("Item 5"))),
            ImageItem(R.drawable.image7, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"))),
        )

        imageSliderAdapter = ImageSliderAdapter(requireActivity(), imagesData)
        circlesAdapter = CirclesAdapter(circleList)

        val mCircleLayoutManager = LinearLayoutManager(requireActivity())
        mCircleLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val transformer = CompositePageTransformer()
        transformer.apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }

        binding.vpViewPager.apply {
            adapter = imageSliderAdapter
            registerOnPageChangeCallback(onPageChangeCallback)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = imagesData.size
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
        }

        binding.rvListRecycle.apply {
            layoutManager = mCircleLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = circlesAdapter
        }
    }

    private fun updateCircleMarker(position: Int) {
        updateImageContentList(imagesData[position])
        circleList.clear()

        (imagesData.indices).forEach { _ ->
            circleList.add(CircleActiveModel(0))
        }
        circleList[position].type = 1
        circlesAdapter.notifyDataSetChanged()
    }

    private fun updateImageContentList(imageItem: ImageItem) {

    }
}