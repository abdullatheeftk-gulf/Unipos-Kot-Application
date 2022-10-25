package com.gulfappdeveloper.project3.repositories

import com.gulfappdeveloper.project3.domain.services.DataStoreService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(
    private val dataStoreService: DataStoreService
) {

    suspend fun updateOperationCount() {
        dataStoreService.updateOperationCount()
    }

    suspend fun saveBaseUrl(baseUrl: String) {
        dataStoreService.saveBaseUrl(baseUrl = baseUrl)
    }

    suspend fun updateSerialNo() {
        dataStoreService.updateSerialNo()
    }


    fun readOperationCount(): Flow<Int> {
        return dataStoreService.readOperationCount()
    }

    fun readBaseUrl(): Flow<String> {
        return dataStoreService.readBaseUrl()
    }

    fun readSerialNo():Flow<Int>{
        return dataStoreService.readSerialNo()
    }


}