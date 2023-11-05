package com.example.quizapp.domain.source

import com.example.quizapp.data.ImagesResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Created by: Kamal.Farghali
 * @Date: 04/11/2023 : 3:48 AM
 */

interface DataRepositorySource {
    suspend fun getImages(): Flow<ImagesResponse>
}