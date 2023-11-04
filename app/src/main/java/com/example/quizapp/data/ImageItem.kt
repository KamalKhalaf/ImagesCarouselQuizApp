package com.example.quizapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Created by: Kamal.Farghali
 * @Date: 02/11/2023 : 7:24 PM
 */

data class ImagesResponse(var data : List<ImageItem>)
@Parcelize
data class ImageItem(val imageResId: Int, var imageContent: List<ImageContent>): Parcelable
@Parcelize
data class ImageContent(val name: String):Parcelable