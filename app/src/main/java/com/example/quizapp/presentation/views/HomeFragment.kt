package com.example.quizapp.presentation.views

import androidx.core.widget.doAfterTextChanged
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
import com.example.quizapp.data.ImageContent
import com.example.quizapp.data.ImageItem
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.presentation.adapter.CirclesAdapter
import com.example.quizapp.presentation.adapter.ImageContentAdapter
import com.example.quizapp.presentation.adapter.ImageSliderAdapter
import com.example.quizapp.presentation.base.BaseFragment
import com.example.quizapp.presentation.viewmodel.ImagesViewModel
import com.example.quizapp.presentation.viewmodel.ImagesViewStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var circlesAdapter: CirclesAdapter
    private lateinit var imageContentAdapter: ImageContentAdapter
    private var imagesData : List<ImageItem> = ArrayList()
    private lateinit var selectedImage : ImageItem
    private var circleList: ArrayList<CircleActiveModel> = ArrayList()
    private val viewmodel: ImagesViewModel by viewModels()
    private var searchJob: Job? = null
    private lateinit var mCircleLayoutManager : LinearLayoutManager
    private var imagesContent: List<ImageContent> = ArrayList()
    val transformer = CompositePageTransformer()

    data class Search(var keyword :String, var position: Int)

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateCircleMarkerAndContentList(position)
        }
    }
    override fun initViews() {
        viewmodel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { status -> handleResponse(status) }
            .launchIn(lifecycleScope)

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
        initAdapters()
    }

    private fun initAdapters() {

        mCircleLayoutManager = LinearLayoutManager(requireActivity())
        mCircleLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        imageSliderAdapter = ImageSliderAdapter(requireActivity(), imagesData!!)
        circlesAdapter = CirclesAdapter(circleList)
        imageContentAdapter = ImageContentAdapter(imagesContent)

        transformer.apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }

        binding.rvListRecycle.apply {
            layoutManager = mCircleLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = circlesAdapter
        }

        binding.rvImageContent.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = false
            adapter = imageContentAdapter
        }

        binding.vpViewPager.apply {
            adapter = imageSliderAdapter
            registerOnPageChangeCallback(onPageChangeCallback)
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
        }
    }

    private fun handleResponse(status: ImagesViewStatus) {
        when (status) {
            is ImagesViewStatus.IsLoading -> {} // Here we can adding the progress loading in case of the data is coming from API
            is ImagesViewStatus.ShowToast -> showShortToast(status.message)
            is ImagesViewStatus.SuccessGetImages -> {
                imagesData = status.response
                updateCircleMarkerAndContentList(0)
            }
            else -> {}
        }
    }

    private fun updateCircleMarkerAndContentList(position: Int) {
        selectedImage = imagesData[position]
        circleList.clear()
        (imagesData.indices).forEach { _ ->
            circleList.add(CircleActiveModel(0))
        }
        circleList[position].type = 1

        binding.vpViewPager.apply {
            offscreenPageLimit = imagesData.size
        }
        imageContentAdapter.updateList(selectedImage.imageContent)
        imageSliderAdapter.update(imagesData)
        circlesAdapter.update(circleList)
    }
}