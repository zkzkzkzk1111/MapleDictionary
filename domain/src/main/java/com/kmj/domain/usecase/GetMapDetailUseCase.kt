package com.kmj.domain.usecase

import com.kmj.domain.repository.MapleStoryRepository
import javax.inject.Inject

class GetMapDetailUseCase @Inject constructor(private val mapleStoryRepository: MapleStoryRepository) {
    operator fun invoke(mapId:Int) =
        mapleStoryRepository.getMapDetail(mapId)
}