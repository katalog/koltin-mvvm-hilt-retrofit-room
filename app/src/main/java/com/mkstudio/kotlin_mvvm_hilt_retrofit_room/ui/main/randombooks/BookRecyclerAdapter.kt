package com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main.randombooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.DB.Book
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.R
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.viewmodel.MainViewModel

class BookRecyclerAdapter(vm: MainViewModel): RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder>() {
    var booklist = mutableListOf<Book>()
    val viewmodel = vm
    lateinit var favoriteBooks: List<Book>

    override fun getItemCount(): Int = booklist.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        favoriteBooks = viewmodel.getFavoriteCache()

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
        val imgBook = itemView.findViewById<ImageView>(R.id.img_rand_recycler_book)
        val txtTitle = itemView.findViewById<TextView>(R.id.txt_rand_recycler_title)
        val txtAuthor = itemView.findViewById<TextView>(R.id.txt_rand_recycler_author)
        val btnFavorite = itemView.findViewById<ImageButton>(R.id.btn_rand_recycler_favorite)

        fun bind(data: Book) {
            Glide.with(itemView.context).load(data.imgurl).
            placeholder(R.drawable.ic_loading_svgrepo).
            error(R.drawable.ic_baseline_error_outline_24).into(imgBook)
            txtTitle.text = data.title
            txtAuthor.text = data.authorname

            if ( favoriteBooks.contains(data)) {btnFavorite.setImageResource(R.drawable.ic_favorite_fill) }

            btnFavorite.setOnClickListener {
                if ( it.isSelected ) {
                    it.isSelected = false
                    btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                    viewmodel.deleteFavorite(data)
                } else {
                    it.isSelected = true
                    btnFavorite.setImageResource(R.drawable.ic_favorite_fill)
                    viewmodel.addFavorite(data)
                }
            }
        }
    }
}