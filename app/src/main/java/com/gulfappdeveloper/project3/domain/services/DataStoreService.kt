package com.gulfappdeveloper.project3.domain.services

import kotlinx.coroutines.flow.Flow

interface DataStoreService {
    suspend fun updateOperationCount()
    suspend fun saveBaseUrl(baseUrl: String)
    suspend fun updateSerialNo()
    suspend fun saveIpAddress(ipAddress: String)
    suspend fun savePortAddress(portAddress: String)
    suspend fun saveUniLicenseData(uniLicenseString: String)


    fun readOperationCount(): Flow<Int>
    fun readBaseUrl(): Flow<String>
    fun readSerialNo(): Flow<Int>
    fun readIpaddress(): Flow<String>
    fun readPortAddress(): Flow<String>
    fun readUniLicenseData(): Flow<String>


}