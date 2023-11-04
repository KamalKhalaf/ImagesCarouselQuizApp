package com.example.quizapp.presentation.base

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var fragments: List<Fragment>
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initViews() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        fragments = ArrayList()
        navController = findNavController(R.id.nav_host_fragment_content_main)
    }
}