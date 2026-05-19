package com.example.bois.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.bois.data.repository.MainRepositoryImpl
import com.example.bois.domain.usecase.GetGameDataUseCase
import com.google.androidgamesdk.GameActivity

class MainActivity : GameActivity() {

    // Simple manual injection for demonstration (Hilt is recommended for production)
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(GetGameDataUseCase(MainRepositoryImpl()))
    }

    companion object {
        init {
            System.loadLibrary("bois")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        viewModel.gameData.observe(this, Observer { data ->
            Log.d("MainActivity", "Received data: $data")
        })

        viewModel.loadGameData()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUi()
        }
    }

    private fun hideSystemUi() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}