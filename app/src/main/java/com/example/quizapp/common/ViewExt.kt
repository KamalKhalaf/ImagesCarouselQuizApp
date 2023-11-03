package com.example.quizapp.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @Created by: Kamal.Farghali
 * @Date: 02/11/2023 : 7:44 PM
 */


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}
