package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.databinding.FragmentSearchBooksBinding
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.base.BaseFragment
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main.randombooks.BookRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.ViewComponentManager

@AndroidEntryPoint
class SearchBooksFragment : BaseFragment<FragmentSearchBooksBinding>(FragmentSearchBooksBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookRecyclerAdapter = BookRecyclerAdapter(viewmodel)
        binding.searchRecycler.adapter = bookRecyclerAdapter

        viewmodel.SearchBookList.observe(viewLifecycleOwner, Observer {
            bookRecyclerAdapter.booklist = it as List<Book>
            bookRecyclerAdapter.notifyDataSetChanged()
        })

        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {

                p0?.isNotEmpty().apply {
                    viewmodel.searchBook(p0!!)
                }

                return true
            }
        })
    }
}