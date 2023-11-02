package com.example.quizapp.presentation.views

import android.os.Bundle
import com.example.quizapp.base.BaseFragment
import com.example.quizapp.databinding.FragmentImageBinding

/**
 * @Created by: Kamal.Farghali
 * @Date: 02/11/2023 : 7:41 PM
 */
class ImageFragment : BaseFragment<FragmentImageBinding>(FragmentImageBinding::inflate) {

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"
        private const val ARG_CARD_OBJECT = "ARG_CARD_OBJECT"

        fun newInstance(position: Int, args: Bundle) = ImageFragment().apply {
            args.putInt(ARG_POSITION, position)
            arguments = args
        }
    }

    override fun initViews() {

    }
}