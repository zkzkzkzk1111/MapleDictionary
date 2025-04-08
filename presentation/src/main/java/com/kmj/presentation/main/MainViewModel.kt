package com.kmj.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmj.domain.usecase.GetItemUseCase
import com.kmj.domain.util.onSuccess
import com.kmj.presentation.model.ItemModel
import com.kmj.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemUseCase : GetItemUseCase,
):ViewModel(){

    private val _items = MutableStateFlow<List<ItemModel>>(emptyList())
    val items = _items.asStateFlow()

    private val _error = MutableStateFlow<Throwable?>(null)
    val error = _error.asStateFlow()

    fun loadItems(){
        viewModelScope.launch {
            try{
                getItemUseCase().collect{ result ->
                    result.onSuccess { items ->
                        val itemList = items.map{it.toPresentation()}
                        _items.value=itemList
                    }
                }
            }catch (e: Exception) {
                Log.e("MainVIewmodel", "Exception in loadMovies", e)
            }
        }
    }

}