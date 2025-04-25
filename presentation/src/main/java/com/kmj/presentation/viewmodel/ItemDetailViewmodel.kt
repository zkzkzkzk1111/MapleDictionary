package com.kmj.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmj.domain.usecase.GetItemDetailUseCase
import com.kmj.domain.util.onFailure
import com.kmj.domain.util.onSuccess
import com.kmj.presentation.model.ItemDetailModel
import com.kmj.presentation.model.toPresentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewmodel @Inject constructor(
    private val getItemDetailUseCase : GetItemDetailUseCase,
 ): ViewModel(){

    private val _itemDetail = MutableStateFlow<ItemDetailModel?>(null)
    val itemDetail = _itemDetail.asStateFlow()

    private val _error = MutableStateFlow<Throwable?>(null)
    val error = _error.asStateFlow()


    fun loadItemDetail(itemId: Int) {

        viewModelScope.launch {
            try {
                _itemDetail.value = null

                getItemDetailUseCase(itemId).collect { result ->
                    result.onSuccess { itemDetail ->
                        val itemDetailModel = itemDetail.toPresentation()
                        _itemDetail.value = itemDetailModel

                    }

                    result.onFailure { error ->
                        Log.e("ItemDetailViewModel", "Failed to load item detail", error)
                    }
                }
            } catch (e: Exception) {
                Log.e("ItemDetailViewModel", "Exception in loadItemDetail", e)
            }
        }
    }
}