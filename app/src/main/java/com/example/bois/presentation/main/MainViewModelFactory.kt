package com.example.bois.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bois.domain.usecase.GetGameDataUseCase

class MainViewModelFactory(private val getGameDataUseCase: GetGameDataUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(getGameDataUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
