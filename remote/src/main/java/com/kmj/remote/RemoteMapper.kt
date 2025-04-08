package com.kmj.remote

interface RemoteMapper<DataModel> {
    fun toData(): DataModel
}
