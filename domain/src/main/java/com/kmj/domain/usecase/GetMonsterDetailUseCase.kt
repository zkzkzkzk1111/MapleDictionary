package com.kmj.domain.usecase

import com.kmj.domain.repository.MapleStoryRepository
import javax.inject.Inject

class GetMonsterDetailUseCase @Inject constructor(private val mapleRepository : MapleStoryRepository){
    operator fun invoke(monsterId:Int) =
        mapleRepository.getMonsterDetail(monsterId)
}