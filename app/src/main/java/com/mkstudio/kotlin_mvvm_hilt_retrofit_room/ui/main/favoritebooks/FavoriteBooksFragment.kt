package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main.favoritebooks

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.databinding.FragmentFavoriteBooksBinding
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteBooksFragment :
    BaseFragment<FragmentFavoriteBooksBinding>(FragmentFavoriteBooksBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteRecyclerAdapter = FavoriteRecyclerAdapter(viewmodel)
        binding.favoriteRecycler.adapter = favoriteRecyclerAdapter

        viewmodel.FavoriteBookList.observe(viewLifecycleOwner, Observer {
            favoriteRecyclerAdapter.favoriteBooks = it
            favoriteRecyclerAdapter.notifyDataSetChanged()
        })

        val itemTouchHelper = ItemTouchHelper(FavoriteRecyclerCallback(context!!, favoriteRecyclerAdapter))
        itemTouchHelper.attachToRecyclerView(binding.favoriteRecycler)
    }
}