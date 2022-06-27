package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main.favoritebooks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.R
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.Util.RecyclerViewSwipeDecorator

class FavoriteRecyclerCallback(private val con:Context, private val adapter:FavoriteRecyclerAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        rv: RecyclerView, vh: RecyclerView.ViewHolder,
        tg: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        // Take action for the swiped item
        when (direction) {
            ItemTouchHelper.LEFT -> {
                Toast.makeText(con, "right swipe", Toast.LENGTH_SHORT).show()
                adapter.removeItem(position)
            }
        }
    }

    override fun onChildDraw(
        c: Canvas, rv: RecyclerView, vh: RecyclerView.ViewHolder,
        dX: Float, dY: Float, act: Int, active: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(c, rv, vh, dX, dY, act, active)
            .addSwipeLeftBackgroundColor(Color.rgb(0xff, 0xfd, 0xd1))
            .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
            .addSwipeLeftLabel("Delete\nFavorite")
            .create()
            .decorate()

        super.onChildDraw(c, rv, vh, dX, dY, act, active)
    }
}