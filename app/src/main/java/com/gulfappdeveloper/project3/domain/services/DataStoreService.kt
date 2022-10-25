package com.gulfappdeveloper.project3.domain.services

import kotlinx.coroutines.flow.Flow

interface DataStoreService {
    suspend fun updateOperationCount()
    suspend fun saveBaseUrl(baseUrl:String)
    suspend fun updateSerialNo()


    fun readOperationCount(): Flow<Int>
    fun readBaseUrl(): Flow<String>
    fun readSerialNo():Flow<Int>

}