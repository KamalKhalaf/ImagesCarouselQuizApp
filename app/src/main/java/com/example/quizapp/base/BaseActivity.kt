package com.example.quizapp.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * @Created by: Kamal.Farghali
 * @Date: 02/11/2023 : 7:03 PM
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB
    protected abstract fun initViews()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}