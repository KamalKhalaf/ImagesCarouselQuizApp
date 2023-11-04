package com.example.quizapp.presentation.views

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.quizapp.data.CircleActiveModel
import com.example.quizapp.data.ImageItem
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.presentation.adapter.CirclesAdapter
import com.example.quizapp.presentation.adapter.ImageContentAdapter
import com.example.quizapp.presentation.adapter.ImageSliderAdapter
import com.example.quizapp.presentation.base.BaseFragment
import com.example.quizapp.presentation.viewmodel.ImagesViewModel
import com.example.quizapp.presentation.viewmodel.ImagesViewStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var circlesAdapter: CirclesAdapter
    private lateinit var imageContentAdapter: ImageContentAdapter
    private var imagesData : List<ImageItem> = ArrayList()
    private var circleList: ArrayList<CircleActiveModel> = ArrayList()
    private val viewmodel: ImagesViewModel by viewModels()


    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateCircleMarkerAndContentList(position)
        }
    }
    override fun initViews() {

        viewmodel.searchImages("L")
        viewmodel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { status -> handleResponse(status) }
            .launchIn(lifecycleScope)

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

    }

    private fun showData(response: List<ImageItem>) {
        imagesData = response
        updateCircleMarkerAndContentList(0)
        imageSliderAdapter.notifyDataSetChanged()
    }

    private fun handleResponse(status: ImagesViewStatus) {
        when (status) {
            is ImagesViewStatus.SuccessGetImages -> showData(status.response)
            else -> {}
        }
    }

    private fun updateCircleMarkerAndContentList(position: Int) {
        imageContentAdapter = ImageContentAdapter(imagesData[position].imageContent)
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