package com.example.quizapp.presentation.views

import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
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
import com.example.quizapp.presentation.adapter.ImageContentAdapter
import com.example.quizapp.presentation.adapter.ImageSliderAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var circlesAdapter: CirclesAdapter
    private lateinit var imageContentAdapter: ImageContentAdapter
    private lateinit var imagesData : List<ImageItem>
    private lateinit var selectedImage : ImageItem
    private var circleList: ArrayList<CircleActiveModel> = ArrayList()

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateCircleMarkerAndContent(position)
        }
    }
    override fun initViews() {

        imagesData = listOf(
            ImageItem(R.drawable.image1, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"), ImageContent("Item 5"))),
            ImageItem(R.drawable.image2, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"))),
            ImageItem(R.drawable.image3, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"))),
            ImageItem(R.drawable.image4, listOf(ImageContent("Item 1"), ImageContent("Item 2"))),
            ImageItem(R.drawable.image5, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"), ImageContent("Item 5"), ImageContent("Item 6"), ImageContent("Item 7"), ImageContent("Item 8"), ImageContent("Item 9"), ImageContent("Item 10"), ImageContent("Item 11"), ImageContent("Item 12"), ImageContent("Item 13"), ImageContent("Item 11"), ImageContent("Item 14"), ImageContent("Item 15"), ImageContent("Item 11"), ImageContent("Item 16"))),
            ImageItem(R.drawable.image6, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"), ImageContent("Item 5"))),
            ImageItem(R.drawable.image7, listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"))),
        )

        imageSliderAdapter = ImageSliderAdapter(requireActivity(), imagesData)
        circlesAdapter = CirclesAdapter(circleList)

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

        val mCircleLayoutManager = LinearLayoutManager(requireActivity())
        mCircleLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvListRecycle.apply {
            layoutManager = mCircleLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = circlesAdapter
        }
        updateCircleMarkerAndContent(0)

        binding.etSearch.doAfterTextChanged { text ->
            lifecycleScope.launch {
                text?.let {
                    if (it.isNotEmpty()) {
                        val filteredItems = selectedImage.imageContent.filter { content -> content.name.contains(it)}
                        imageContentAdapter.updateList(filteredItems)
                    } else {
                        imageContentAdapter.updateList(selectedImage.imageContent)
                    }
                }
            }
        }
    }

    private fun updateCircleMarkerAndContent(position: Int) {
        selectedImage = imagesData[position]

        imageContentAdapter = ImageContentAdapter(selectedImage.imageContent)
        binding.rvImageContent.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = false
            adapter = imageContentAdapter
            imageContentAdapter.notifyDataSetChanged()
        }

        circleList.clear()
        (imagesData.indices).forEach { _ ->
            circleList.add(CircleActiveModel(0))
        }
        circleList[position].type = 1
        circlesAdapter.notifyDataSetChanged()
    }
}