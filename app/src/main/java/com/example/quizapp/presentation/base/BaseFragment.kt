package com.example.quizapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.quizapp.common.Inflate

/**
 * @Created by: Kamal.Farghali
 * @Date: 02/11/2023 : 7:43 PM
 */

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    abstract fun initViews()
    val Any.TAG: String
        get() {
            return javaClass.simpleName
        }

    fun showShortToast(text: String) = Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
    fun showShortToast(txtRes: Int) = Toast.makeText(requireActivity(), txtRes, Toast.LENGTH_SHORT).show()
}