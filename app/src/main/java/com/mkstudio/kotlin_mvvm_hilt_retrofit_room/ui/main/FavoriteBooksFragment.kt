package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main

import android.os.Bundle
import android.view.View
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.databinding.FragmentFavoriteBooksBinding
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteBooksFragment : BaseFragment<FragmentFavoriteBooksBinding>(FragmentFavoriteBooksBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.getRandomBooks()
    }
}