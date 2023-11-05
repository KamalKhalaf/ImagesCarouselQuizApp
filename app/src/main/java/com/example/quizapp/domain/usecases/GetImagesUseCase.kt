package com.example.quizapp.domain.usecases

import com.example.quizapp.data.ImagesResponse
import com.example.quizapp.domain.source.DataRepositorySource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Created by: Kamal.Farghali
 * @Date: 04/11/2023 : 3:35 AM
 */

class GetImagesUseCase @Inject constructor(private val imagesRepo: DataRepositorySource) {

    suspend fun invoke() : Flow<ImagesResponse> {
        return imagesRepo.getImages()
    }
}