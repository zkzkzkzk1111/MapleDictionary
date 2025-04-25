package com.kmj.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmj.domain.usecase.GetMapDetailUseCase
import com.kmj.domain.usecase.GetMapUseCase

import com.kmj.domain.util.onFailure
import com.kmj.domain.util.onSuccess
import com.kmj.presentation.model.MapDetailModel
import com.kmj.presentation.model.MapModel
import com.kmj.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMapDetailUseCase: GetMapDetailUseCase,
    private val getMapUseCase: GetMapUseCase,
): ViewModel(){

    val _mapDetail = MutableStateFlow<MapDetailModel?>(null)
    val mapDetail = _mapDetail.asStateFlow()

    private val _error = MutableStateFlow<Throwable?>(null)
    val error = _error.asStateFlow()


    val _maps = MutableStateFlow<List<MapModel>>(emptyList())
    val maps = _maps.asStateFlow()




    fun loadMaps() {
        viewModelScope.launch {
            try {
                _maps.value = emptyList()
                getMapUseCase().collect { result ->
                    result.onSuccess { map ->
                        val mapList = map.map { it.toPresentation() }
                        _maps.value = mapList
                    }
                    result.onFailure { error ->
                        Log.e("MapDetailViewmodel", "Failed to load loadMaps", error)
                    }
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception in loadMonsters", e)
            }
        }
    }

    fun loadMapDetail(mapId:Int) {
        viewModelScope.launch {
            try {
                _mapDetail.value = null
                getMapDetailUseCase(mapId).collect { result ->
                    result.onSuccess { mapDetail ->
                        val mapDetailModel = mapDetail.toPresentation()
                        _mapDetail.value = mapDetailModel
                    }
                    result.onFailure { error ->
                        Log.e("MapViewModel", "Failed to load map detail", error)
                    }
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception in loadMonsters", e)
            }
        }
    }

}