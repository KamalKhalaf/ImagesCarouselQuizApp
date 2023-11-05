package com.example.quizapp.domain.source

import com.example.quizapp.R
import com.example.quizapp.data.ImageContent
import com.example.quizapp.data.ImageItem
import com.example.quizapp.data.ImagesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @Created by: Kamal.Farghali
 * @Date: 04/11/2023 : 3:58 AM
 */
class DataRepositorySourceImpl : DataRepositorySource {

    override suspend fun getImages(): Flow<ImagesResponse> {
        return flow {
            emit(
                getImagesFromLocalStorage()
            )
        }
    }

    private fun getImagesFromLocalStorage(): ImagesResponse {
        return ImagesResponse(data = listOf(
            ImageItem(R.drawable.image1, imageContent = listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"), ImageContent("Item 5"))),
            ImageItem(R.drawable.image2, imageContent = listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"))),
            ImageItem(R.drawable.image3, imageContent = listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"))),
            ImageItem(R.drawable.image4, imageContent = listOf(ImageContent("Item 1"), ImageContent("Item 2"))),
            ImageItem(R.drawable.image5, imageContent = listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"), ImageContent("Item 5"), ImageContent("Item 6"), ImageContent("Item 7"), ImageContent("Item 8"), ImageContent("Item 9"), ImageContent("Item 10"), ImageContent("Item 11"), ImageContent("Item 12"), ImageContent("Item 13"), ImageContent("Item 11"), ImageContent("Item 14"), ImageContent("Item 15"), ImageContent("Item 11"), ImageContent("Item 16"))),
            ImageItem(R.drawable.image6, imageContent = listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"), ImageContent("Item 4"), ImageContent("Item 5"))),
            ImageItem(R.drawable.image7, imageContent = listOf(ImageContent("Item 1"), ImageContent("Item 2"), ImageContent("Item 3"))),
        ))
    }
}