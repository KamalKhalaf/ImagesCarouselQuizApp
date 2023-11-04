package com.example.quizapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.ImageItem
import com.example.quizapp.domain.usecases.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Created by: Kamal.Farghali
 * @Date: 04/11/2023 : 3:31 AM
 */

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ImagesViewStatus>(ImagesViewStatus.Init)
    val state: StateFlow<ImagesViewStatus> get() = _state

    private val keywordFlow = MutableSharedFlow<String>()
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            keywordFlow.collectLatest {
                searchJob?.cancel()
                getImagesUseCase.invoke(it)
                    .onStart {
                        _state.value = ImagesViewStatus.IsLoading(true)
                        delay(1000)
                    }
                    .catch { exception ->
                        _state.value = ImagesViewStatus.ShowToast(exception.message.toString())
                        _state.value = ImagesViewStatus.IsLoading(false)
                    }
                    .collect { result ->
                        _state.value = ImagesViewStatus.IsLoading(false)
                        _state.value = ImagesViewStatus.SuccessGetImages(result.data)

                    }
            }
        }
    }

    fun searchImages(keyword: String) {
        viewModelScope.launch {
            keywordFlow.emit(keyword)
        }
    }

    fun resetSearch() {
        searchJob?.cancel()
    }
}

sealed class ImagesViewStatus {
    object Init : ImagesViewStatus()
    data class IsLoading(val isLoading: Boolean) : ImagesViewStatus()
    data class ShowToast(val message: String) : ImagesViewStatus()
    data class SuccessGetImages(val response: List<ImageItem>) : ImagesViewStatus()
}