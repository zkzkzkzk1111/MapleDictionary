package com.kmj.domain.usecase

import com.kmj.domain.repository.MapleStoryRepository
import javax.inject.Inject

class GetNPCUseCase@Inject constructor(private val mapleRepository : MapleStoryRepository){
    operator fun invoke() =
        mapleRepository.getNPC()
}