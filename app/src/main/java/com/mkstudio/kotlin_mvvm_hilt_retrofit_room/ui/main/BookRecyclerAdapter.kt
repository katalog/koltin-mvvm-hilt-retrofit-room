package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.R

class BookRecyclerAdapter(): RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder>() {
    var booklist = mutableListOf<Book>()
    override fun getItemCount(): Int = booklist.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.random_recycler_item, parent, false)
        return ViewHolder(vh)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = booklist[position]
        holder.apply {
            bind(item)
            itemView.tag = item
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgBook = itemView.findViewById<ImageView>(R.id.img_recycler_book)
        val txtTitle = itemView.findViewById<TextView>(R.id.txt_recycler_title)
        val txtAuthor = itemView.findViewById<TextView>(R.id.txt_recycler_author)

        fun bind(data: Book) {
            Glide.with(itemView.context).load(data.imgurl).
            placeholder(R.drawable.ic_loading_svgrepo).
            error(R.drawable.ic_baseline_error_outline_24).into(imgBook)
            txtTitle.text = data.title
            txtAuthor.text = data.authorname
        }
    }
}
