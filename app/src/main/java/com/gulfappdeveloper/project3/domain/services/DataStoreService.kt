package com.gulfappdeveloper.project3.domain.services

import kotlinx.coroutines.flow.Flow

interface DataStoreService {
    suspend fun updateOperationCount()
    suspend fun saveBaseUrl(baseUrl:String)


    fun readOperationCount(): Flow<Int>
    fun readBaseUrl(): Flow<String>

}