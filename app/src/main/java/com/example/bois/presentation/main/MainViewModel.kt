package com.example.bois.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bois.domain.model.Resource
import com.example.bois.domain.usecase.GetGameDataUseCase
import com.example.bois.domain.usecase.ResourceTickerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGameDataUseCase: GetGameDataUseCase,
    private val resourceTickerUseCase: ResourceTickerUseCase
) : ViewModel() {
    
    private val _gameData = MutableStateFlow("")
    val gameData: StateFlow<String> = _gameData.asStateFlow()

    private val _resources = MutableStateFlow<List<Resource>>(emptyList())
    val resources: StateFlow<List<Resource>> = _resources.asStateFlow()

    init {
        startTicker()
        loadGameData()
    }

    fun loadGameData() {
        viewModelScope.launch {
            val data = getGameDataUseCase()
            _gameData.value = data
        }
    }

    fun startTicker() {
        viewModelScope.launch {
            resourceTickerUseCase().collectLatest { updatedResources ->
                _resources.value = updatedResources
            }
        }
    }
}
