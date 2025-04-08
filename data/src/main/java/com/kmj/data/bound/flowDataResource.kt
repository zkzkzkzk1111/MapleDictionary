package com.kmj.data.bound

import com.kmj.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> flowDataResource(block: suspend () -> T): Flow<ApiResult<T>> = flow {
    try {
        // 로딩 상태 먼저 emit (옵션)
        emit(ApiResult.Loading(null))

        // 데이터 가져오기
        val data = block()

        // 성공 상태 emit
        emit(ApiResult.Success(data))
    } catch (e: Exception) {
        // 오류 상태 emit
        emit(ApiResult.Failure(e))
    }
}