package com.example.quizapp.domain.usecases

import com.example.quizapp.data.ImagesResponse
import com.example.quizapp.domain.source.DataRepositorySource
import kotlinx.coroutines.flow.Flow

/**
 * @Created by: Kamal.Farghali
 * @Date: 04/11/2023 : 3:35 AM
 */

class GetImagesUseCase(private val imagesRepo: DataRepositorySource) {

    suspend fun invoke(search : String) : Flow<ImagesResponse> {
        return imagesRepo.getImages(search)
    }
}