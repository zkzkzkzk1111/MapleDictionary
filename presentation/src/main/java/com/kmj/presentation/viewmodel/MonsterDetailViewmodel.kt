package com.kmj.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmj.domain.usecase.GetMapDetailUseCase
import com.kmj.domain.usecase.GetMonsterDetailUseCase
import com.kmj.domain.util.onFailure
import com.kmj.domain.util.onSuccess
import com.kmj.presentation.model.MapDetailModel
import com.kmj.presentation.model.MonsterDetailModel
import com.kmj.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonsterDetailViewmodel @Inject constructor(
    private val getMonsterDetailUseCase: GetMonsterDetailUseCase,
    private val getMapDetailUseCase : GetMapDetailUseCase
): ViewModel() {
    val _monsterDetail = MutableStateFlow<MonsterDetailModel?>(null)
    val monsterDetail = _monsterDetail.asStateFlow()

    private val _error = MutableStateFlow<Throwable?>(null)
    val error = _error.asStateFlow()

    private val _monsterMapDetails = MutableStateFlow<List<MapDetailModel>>(emptyList())
    val monsterMapDetails = _monsterMapDetails.asStateFlow()

    private val _mapmonsterMapDetails = MutableStateFlow<List<MonsterDetailModel>>(emptyList())
    val mapmonsterMapDetails = _mapmonsterMapDetails.asStateFlow()

    fun loadMonsterDetail(monsterId:Int){

        viewModelScope.launch {
            try {
                _monsterDetail.value = null
                getMonsterDetailUseCase(monsterId).collect { result ->
                    result.onSuccess { monsterDetail ->
                        val monsterDetailModel = monsterDetail.toPresentation()
                        _monsterDetail.value = monsterDetailModel
                    }
                    result.onFailure { error ->
                        Log.e("MonsterDetailViewmodel", "Failed to load item detail", error)
                    }
                }

            } catch (e: Exception) {
                Log.e("MonsterDetailViewmodel", "Exception in loadItemDetail", e)
            }
        }

    }

    fun loadMonsterMapDetail(foundAt: List<Int>) {
        viewModelScope.launch {
            try {

                val mapDetailsList = mutableListOf<MapDetailModel>()

                foundAt.forEach { mapId ->
                    try {
                        getMapDetailUseCase(mapId).collect { result ->
                            result.onSuccess { mapDetail ->
                                val mapDetailModel = mapDetail.toPresentation()
                                mapDetailsList.add(mapDetailModel)
                            }
                            result.onFailure { error ->
                                Log.e("MonsterDetailViewmodel", "Failed to load map detail for ID: $mapId", error)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("MonsterDetailViewmodel", "Exception processing mapId: $mapId", e)
                    }
                }
                _monsterMapDetails.value = mapDetailsList

            } catch (e: Exception) {
                Log.e("MonsterDetailViewmodel", "Exception in loadMonsterMapDetail", e)
            }
        }
    }


    fun loadMapMonsterDetail(mobs: List<Int>) {
        viewModelScope.launch {
            try {

                val monsterDetailsList = mutableListOf<MonsterDetailModel>()

                mobs.forEach { mapId ->
                    try {
                        getMonsterDetailUseCase(mapId).collect { result ->
                            result.onSuccess { monsterDetail ->
                                val monsterDetailModel = monsterDetail.toPresentation()
                                monsterDetailsList.add(monsterDetailModel)
                            }
                            result.onFailure { error ->
                                Log.e("MonsterDetailViewmodel", "Failed to load map detail for ID: $mapId", error)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("MonsterDetailViewmodel", "Exception processing mapId: $mapId", e)
                    }
                }
                _mapmonsterMapDetails.value = monsterDetailsList

            } catch (e: Exception) {
                Log.e("MonsterDetailViewmodel", "Exception in loadMonsterMapDetail", e)
            }
        }
    }
}