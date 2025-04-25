package com.kmj.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmj.domain.usecase.GetNPCUseCase
import com.kmj.domain.util.onSuccess
import com.kmj.presentation.model.NPCModel
import com.kmj.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NPCViewModel @Inject constructor(
    private val getNPCUseCase : GetNPCUseCase
) : ViewModel(){
    val _npc = MutableStateFlow<List<NPCModel>>(emptyList())
    val npc =_npc.asStateFlow()

    fun loadNPCs(){
        viewModelScope.launch {
            try{
                _npc.value = emptyList()
                getNPCUseCase().collect{ result ->
                    result.onSuccess { npcs ->
                        val itemList = npcs.map{it.toPresentation()}
                        _npc.value=itemList
                    }
                }
            }catch (e: Exception) {
                Log.e("NPCViewModel", "Exception in loadMovies", e)
            }
        }
    }
}