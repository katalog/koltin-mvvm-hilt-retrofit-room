package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.API.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.databinding.FragmentRandomBooksBinding
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomBooksFragment : BaseFragment<FragmentRandomBooksBinding>(FragmentRandomBooksBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookRecyclerAdapter = BookRecyclerAdapter()
        binding.randomRecycler.adapter = bookRecyclerAdapter

        viewmodel.Booklist.observe(viewLifecycleOwner, Observer {
            bookRecyclerAdapter.booklist = it as MutableList<Book>
            bookRecyclerAdapter.notifyDataSetChanged()
        })

        viewmodel.getRandomBooks()
    }
}
