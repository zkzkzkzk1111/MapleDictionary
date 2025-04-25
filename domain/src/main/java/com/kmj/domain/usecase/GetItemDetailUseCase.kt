package com.kmj.domain.usecase

import com.kmj.domain.repository.MapleStoryRepository
import javax.inject.Inject

class GetItemDetailUseCase @Inject constructor(private val mapleRepository : MapleStoryRepository){
    operator fun invoke(itemId:Int) =
        mapleRepository.getItemDetail(itemId)
}