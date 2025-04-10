package com.kmj.domain.usecase

import com.kmj.domain.repository.MapleStoryRepository
import javax.inject.Inject

class GetMonsterUseCase @Inject constructor(private val mapleStoryRepository: MapleStoryRepository) {
    operator fun invoke(page: Int = 0, maxEntries: Int = 100) =
        mapleStoryRepository.getMonsters(page, maxEntries)
}