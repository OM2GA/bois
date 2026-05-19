package com.example.bois.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bois.domain.usecase.GetGameDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getGameDataUseCase: GetGameDataUseCase) : ViewModel() {
    
    private val _gameData = MutableLiveData<String>()
    val gameData: LiveData<String> = _gameData

    fun loadGameData() {
        viewModelScope.launch {
            val data = getGameDataUseCase()
            _gameData.value = data
        }
    }
}
