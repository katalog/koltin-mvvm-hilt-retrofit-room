package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main

import android.os.Bundle
import android.view.View
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.databinding.FragmentSearchBooksBinding
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBooksFragment : BaseFragment<FragmentSearchBooksBinding>(FragmentSearchBooksBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}