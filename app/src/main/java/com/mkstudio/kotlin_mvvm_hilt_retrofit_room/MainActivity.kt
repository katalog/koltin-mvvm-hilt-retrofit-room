package com.mkstudio.kotlin_mvvm_hilt_retrofit_room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.databinding.ActivityMainBinding
import com.mkstudio.kotlin_mvvm_hilt_retrofit_room.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
    }
}