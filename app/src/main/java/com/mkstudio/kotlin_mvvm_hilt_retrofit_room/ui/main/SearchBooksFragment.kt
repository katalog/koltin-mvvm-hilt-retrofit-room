package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.R
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.databinding.FragmentSearchBooksBinding
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.base.BaseFragment
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main.randombooks.BookRecyclerAdapter
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel.SearchBooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBooksFragment :
    BaseFragment<FragmentSearchBooksBinding>(FragmentSearchBooksBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchViewModel: SearchBooksViewModel by viewModels()

        val bookRecyclerAdapter = BookRecyclerAdapter(viewmodel)
        binding.searchRecycler.adapter = bookRecyclerAdapter

        searchViewModel.SearchBookList.observe(viewLifecycleOwner, Observer {
            bookRecyclerAdapter.booklist = it as List<Book>
            bookRecyclerAdapter.notifyDataSetChanged()
        })

        searchViewModel.SearchFailed.observe(viewLifecycleOwner, Observer {
            if (it != true) return@Observer
            Toast.makeText(context, R.string.search_failed, Toast.LENGTH_SHORT).show()
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    searchViewModel.searchBook(it)
                }
                return true
            }
        })
    }
}