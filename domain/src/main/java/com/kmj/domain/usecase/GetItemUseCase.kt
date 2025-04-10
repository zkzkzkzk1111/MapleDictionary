package com.kmj.domain.usecase

import com.kmj.domain.repository.MapleStoryRepository
import javax.inject.Inject

class GetItemUseCase @Inject constructor(private val mapleRepository : MapleStoryRepository ){
    operator fun invoke(page: Int = 0, maxEntries: Int = 100) =
        mapleRepository.getItems(page, maxEntries)
}