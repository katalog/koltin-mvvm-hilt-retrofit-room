package com.mkstudio.kotlin_mvvm_hilt_retrofit_room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.databinding.ActivityMainBinding
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.base.Screen
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main.FavoriteBooksFragment
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main.RandomBooksFragment
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main.SearchBooksFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val mapFragments = mutableMapOf<Int, Screen>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buildFragments()

        if (savedInstanceState == null) {
            openFragment(R.id.tab_favorite)
        }

        binding.bottomNavi.setOnItemSelectedListener {
            return@setOnItemSelectedListener openFragment(it.itemId)
        }
    }

    private fun buildFragments() {
        mapFragments[R.id.tab_random] = Screen(RandomBooksFragment(), "Random_Fragment")
        mapFragments[R.id.tab_search] = Screen(SearchBooksFragment(), "Search_Fragment")
        mapFragments[R.id.tab_favorite] = Screen(FavoriteBooksFragment(), "Favorite_Fragment")
    }

    private fun openFragment(fragmentID: Int): Boolean {
        mapFragments[fragmentID]?.let { it ->
            supportFragmentManager.findFragmentByTag(it.fragTAG) ?: apply {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frames, it.frag)
                    .commitNow()
                return true
            }
        }
        return false
    }
}