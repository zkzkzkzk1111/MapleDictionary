package com.kmj.presentation.screen.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmj.domain.usecase.GetItemUseCase
import com.kmj.domain.usecase.GetMapUseCase
import com.kmj.domain.usecase.GetMonsterUseCase
import com.kmj.domain.util.onSuccess
import com.kmj.presentation.model.ItemModel
import com.kmj.presentation.model.MapModel
import com.kmj.presentation.model.MonsterModel
import com.kmj.presentation.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemUseCase : GetItemUseCase,
    private val getMonsterUseCase : GetMonsterUseCase,
    private val getMapUseCase: GetMapUseCase
):ViewModel(){

    private val _items = MutableStateFlow<List<ItemModel>>(emptyList())
    val items = _items.asStateFlow()

    private val _monsters = MutableStateFlow<List<MonsterModel>>(emptyList())
    val monsters = _monsters.asStateFlow()

    private val _error = MutableStateFlow<Throwable?>(null)
    val error = _error.asStateFlow()


    private val pageSize = 5

    private var currentPage = 0
    private var isLoading = false
    private var hasMoreItems = true


    private var monsterCurrentPage = 0
    private var monsterIsLoading = false
    private var monsterHasMoreItems = true

    init {
        loadMoreItems()
        loadMoreMonsters()
    }

    fun loadMoreItems() {
        if (isLoading || !hasMoreItems) return

        viewModelScope.launch {
            isLoading = true

            try {
                getItemUseCase(page = currentPage, maxEntries = pageSize).collect { result ->
                    result.onSuccess { newItems ->
                        val newItemModels = newItems.map { it.toPresentation() }

                        if (newItemModels.isEmpty()) {
                            hasMoreItems = false
                        } else {

                            _items.update { currentItems -> currentItems + newItemModels }
                            currentPage++
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading items", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMoreMonsters() {
        Log.d("MonsterDebug", "loadMoreMonsters called: isLoading=$monsterIsLoading, hasMore=$monsterHasMoreItems, page=$monsterCurrentPage")
        if (monsterIsLoading || !monsterHasMoreItems) return

        viewModelScope.launch {
            monsterIsLoading = true
            Log.d("MonsterDebug", "Loading started for page $monsterCurrentPage")

            try {
                getMonsterUseCase(page = monsterCurrentPage, maxEntries = pageSize).collect { result ->
                    Log.d("MonsterDebug", "Got result for monsters")
                    result.onSuccess { newMonsters ->
                        val newMonsterModels = newMonsters.map { it.toPresentation() }
                        Log.d("MonsterDebug", "Success! Got ${newMonsterModels.size} new monsters")
                        if (newMonsterModels.isEmpty()) {
                            monsterHasMoreItems = false
                            Log.d("MonsterDebug", "No more monsters available")
                        } else {
                            val oldSize = _monsters.value.size
                            _monsters.update { currentMonsters -> currentMonsters + newMonsterModels }
                            val newSize = _monsters.value.size
                            Log.d("MonsterDebug", "Updated monsters: $oldSize -> $newSize")
                            monsterCurrentPage++
                            Log.d("MonsterDebug", "Incremented page to $monsterCurrentPage")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading monsters", e)
            } finally {
                monsterIsLoading = false
            }
        }
    }

    fun loadMonsters() {
        viewModelScope.launch {
            try {
                monsterCurrentPage = 0
                _monsters.value = emptyList()
                monsterHasMoreItems = true

                getMonsterUseCase(page = 0, maxEntries = pageSize).collect { result ->
                    result.onSuccess { monsters ->
                        val monsterList = monsters.map { it.toPresentation() }
                        _monsters.value = monsterList
                        monsterCurrentPage = 1
                    }
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception in loadMonsters", e)
            }
        }
    }
    fun loadItems(){
        viewModelScope.launch {
            try{

                currentPage = 0
                _items.value = emptyList()
                hasMoreItems = true

                getItemUseCase(page = 0, maxEntries = pageSize).collect{ result ->
                    result.onSuccess { items ->
                        val itemList = items.map{it.toPresentation()}
                        _items.value=itemList
                        currentPage = 1
                    }
                }
            }catch (e: Exception) {
                Log.e("MainVIewmodel", "Exception in loadMovies", e)
            }
        }
    }

}